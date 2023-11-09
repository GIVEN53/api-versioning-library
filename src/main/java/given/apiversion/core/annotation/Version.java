package given.apiversion.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You can use this annotation to specify the version of the API.
 * <p>
 * ex) @Version({"1.1", "2.0"})
 * <p>
 * It will be mapped to {uri-prefix}/v1.1, {uri-prefix}/v2.0
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Version {
    String[] value();
}
