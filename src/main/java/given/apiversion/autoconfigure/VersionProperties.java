package given.apiversion.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mapping properties that match a prefix to corresponding objects in the context of {@link ConfigurationProperties}
 *
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "api.version")
public class VersionProperties {
    /**
     * The prefix of URI.
     * <p>
     * ex) /api
     * <p>
     * If you set this property, the URI will be mapped to {uri-prefix}/v1.1, {uri-prefix}/v2.0
     * <p>
     * default: ""
     */
    private String uriPrefix = "";

    public String getUriPrefix() {
        return uriPrefix;
    }

    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    public boolean isNotBlankUriPrefix() {
        return uriPrefix != null && !uriPrefix.isBlank();
    }
}
