package given.apiversion.core.servlet;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import given.apiversion.autoconfigure.VersionProperties;

/**
 * Spring Web Mvc provides a {@link WebMvcRegistrations} interface for customization support.
 * {@link WebMvcAutoConfiguration.EnableWebMvcConfiguration} sets it to null as the default value.
 * Therefore, we have the flexibility to provide our own implementation.
 *
 * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration#createRequestMappingHandlerMapping()
 * @since 0.1.0
 */
public class VersionWebMvcRegistrations implements WebMvcRegistrations {
    private final VersionProperties versionProperties;

    public VersionWebMvcRegistrations(VersionProperties versionProperties) {
        this.versionProperties = versionProperties;
    }

    /**
     * Overrides the method to provide a custom {@link RequestMappingHandlerMapping}.
     *
     * @return a custom {@link RequestMappingHandlerMapping} to support versioning.
     * @since 0.1.0
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new VersionRequestMappingHandlerMapping(versionProperties);
    }
}
