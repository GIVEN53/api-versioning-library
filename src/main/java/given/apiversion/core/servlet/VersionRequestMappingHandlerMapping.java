package given.apiversion.core.servlet;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import given.apiversion.autoconfigure.VersionProperties;
import given.apiversion.core.annotation.ApiVersion;
import given.apiversion.core.util.VersionValidator;

/**
 * Creates RequestMappingInfo instances from type and method-level @RequestMapping annotations in @Controller classes.
 * Overrides {@link RequestMappingHandlerMapping#getMappingForMethod(Method, Class)} method.
 *
 * @see RequestMappingHandlerMapping
 * @since 0.1.0
 */
public class VersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private static final String VERSION_PREFIX = "/v";
    private final VersionProperties versionProperties;
    private final VersionValidator versionValidator = new VersionValidator();

    public VersionRequestMappingHandlerMapping(VersionProperties versionProperties) {
        this.versionProperties = versionProperties;
    }

    /**
     * Uses method and type-level @RequestMapping annotations to create the RequestMappingInfo.
     *
     * @param method      Controller method
     * @param handlerType Controller class
     * @return RequestMappingInfo or null if the method does not have a @RequestMapping annotation.
     * @since 0.1.0
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info == null) {
            return info;
        }
        info = combineRequestMappingInfoOfHandlerType(handlerType, info);

        ApiVersion apiVersion = getVersionAnnotation(method, handlerType);
        if (apiVersion == null) {
            return info;
        }
        return combineRequestMappingInfoOfVersion(info, apiVersion);
    }

    /**
     * Creates {@link RequestMappingInfo} from {@link RequestMapping} annotation of annotatedElement.
     * <p>
     * If annotatedElement has the @RequestMapping annotation,
     * delegate to {@link RequestMappingHandlerMapping#createRequestMappingInfo(RequestMapping, RequestCondition)}
     * <p>
     * Otherwise, return null.
     *
     * @since 0.1.0
     */
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = this.getRequestCondition(element);
        if (requestMapping != null) {
            return super.createRequestMappingInfo(requestMapping, condition);
        }
        return null;
    }

    /**
     * TODO
     */
    private RequestCondition<?> getRequestCondition(AnnotatedElement element) {
        if (element instanceof Class<?> clazz) {
            return getCustomTypeCondition(clazz);
        }
        return getCustomMethodCondition((Method)element);
    }

    /**
     * Combines RequestMappingInfo of handlerType
     * <p>
     * If handlerType has @RequestMapping annotation, combine it with method's RequestMappingInfo.
     * <p>
     * ex) handlerType's @RequestMapping("/api"), method's @RequestMapping("/signup") -> /api/signup
     * <p><br>
     * If {@link RequestMappingInfo} is empty, set default mapping.
     * <p>
     * ex) handlerType's @RequestMapping, method's @RequestMapping -> "", "/"
     *
     * @param handlerType Controller class
     * @param info        RequestMappingInfo of method
     * @return combined RequestMappingInfo
     * @since 0.1.0
     */
    private RequestMappingInfo combineRequestMappingInfoOfHandlerType(Class<?> handlerType, RequestMappingInfo info) {
        RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
        if (typeInfo != null) {
            info = typeInfo.combine(info);
        }
        // if (info.isEmptyMapping()) { TODO This method is applicable starting from Spring 6.0.10, Spring Boot 3.1.1 and above.
        //     info = info.mutate().paths("", "/").options(super.getBuilderConfiguration()).build();
        // }
        return info;
    }

    /**
     * {@link AnnotationUtils#findAnnotation} method searches for the specified annotation type
     * not only on the given element but also hierarchically on its super elements.
     * <p>
     * {@link AnnotationUtils#getAnnotation} method is used to find a specified annotation type on the given element.
     * <p>
     * So we use {@link AnnotationUtils#getAnnotation} method to find {@link ApiVersion} annotation
     * because it allows us to specifically target and retrieve the {@link ApiVersion} annotation on a given element,
     * such as a class or method. However, it's important to note that the implementation may change in the future.
     *
     * @since 0.1.0
     */
    private ApiVersion getVersionAnnotation(Method method, Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.getAnnotation(method, ApiVersion.class);
        if (apiVersion == null) {
            apiVersion = AnnotationUtils.getAnnotation(handlerType, ApiVersion.class);
        }
        return apiVersion;
    }

    /**
     * Combines RequestMappingInfo of @ApiVersion annotation
     * <p>
     * ex) @ApiVersion({"1.1", "2.0"}), method's @RequestMapping("/signup") -> /v1.1/signup, /v2.0/signup
     * <p><br>
     * If {@link VersionProperties#ExistUriPrefix()} is true, combine it with method's RequestMappingInfo.
     * <p>
     * ex) @ApiVersion({"1.1", "2.0"}), uriPrefix="/api", method's @RequestMapping("/signup")
     * <p>
     * -> /api/v1.1/signup, /api/v2.0/signup
     *
     * @param info       RequestMappingInfo of method
     * @param apiVersion @ApiVersion annotation
     * @return combined RequestMappingInfo
     * @since 0.1.0
     */
    private RequestMappingInfo combineRequestMappingInfoOfVersion(RequestMappingInfo info, ApiVersion apiVersion) {
        String[] versions = apiVersion.value();
        versionValidator.validate(versions);

        if (versionProperties.ExistUriPrefix()) {
            String prefix = versionProperties.getUriPrefix().trim() + VERSION_PREFIX;
            return combine(() -> createPaths(versions, prefix), info);
        }
        return combine(() -> createPaths(versions, VERSION_PREFIX), info);
    }

    private String[] createPaths(String[] versions, String prefix) {
        return Arrays.stream(versions)
                .map(v -> prefix + v)
                .toArray(String[]::new);
    }

    private RequestMappingInfo combine(Supplier<String[]> supplier, RequestMappingInfo info) {
        return RequestMappingInfo.paths(supplier.get())
                .options(super.getBuilderConfiguration())
                .build()
                .combine(info);
    }
}
