package given.apiversion.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} : Mapping properties that match prefix among properties to objects.
 *
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "api.version")
public class VersionProperties {
    /**
     * The prefix of URI.
     * <p>
     * ex) /api
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
