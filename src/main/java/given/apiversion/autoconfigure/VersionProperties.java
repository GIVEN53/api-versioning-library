package given.apiversion.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import given.apiversion.core.annotation.ApiVersion;

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
     *
     * @since 0.1.0
     */
    private String uriPrefix = "";

    /**
     * This property can be useful when <strong>maintaining previous API</strong> specs that do not apply {@link ApiVersion} annotation.
     * <p>
     * Set this property to true, if you want to keep the old API already using the URI prefix
     * and share it with the new API.
     * <p>
     * ex) previous API is /api/user -> modify it to /user, set uri-prefix to /api, set sharingUriPrefix to true.
     * It will be mapped to /api/user and isolated from the new APIs that use {@link ApiVersion} annotation.
     *
     * @since 0.2.0
     */
    private boolean sharingUriPrefix = false;

    public String getUriPrefix() {
        return uriPrefix;
    }

    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    public boolean ExistUriPrefix() {
        return uriPrefix != null && !uriPrefix.isBlank();
    }

    public boolean isSharingUriPrefix() {
        return sharingUriPrefix;
    }

    public void setSharingUriPrefix(boolean sharingUriPrefix) {
        this.sharingUriPrefix = sharingUriPrefix;
    }
}
