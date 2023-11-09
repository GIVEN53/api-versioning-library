package given.apiversion.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import given.apiversion.core.servlet.VersionWebMvcRegistrations;

/**
 * {@link EnableConfigurationProperties} registers the bean of the object using {@link ConfigurationProperties}.
 * <p>
 * {@link ConditionalOnMissingBean} registers the bean if there is no bean of the same type in the context.
 *
 * @since 0.1.0
 */
@Configuration
@EnableConfigurationProperties(VersionProperties.class)
public class VersionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebMvcRegistrations webMvcRegistrations(VersionProperties versionProperties) {
        return new VersionWebMvcRegistrations(versionProperties);
    }
}
