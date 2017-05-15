package org.educama.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

/**
 * Configuration properties for CORS related settings.
 */
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private CorsConfiguration corsProp = new CorsConfiguration().applyPermitDefaultValues();

    @NotEmpty(message = "AllowedMethods must not be empty")
    private List<String> allowedMethods = Collections.singletonList(CorsConfiguration.ALL);

    @NotEmpty(message = "UrlMapping must not be empty")
    private String urlMapping = "/**";

    public void setAllowCredentials(boolean allowCredentials) {
        this.corsProp.setAllowCredentials(allowCredentials);
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        for (String i : allowedHeaders) {
            this.corsProp.addAllowedHeader(i);
        }
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedMethods() {
        return this.allowedMethods;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.corsProp.setAllowedOrigins(allowedOrigins);
    }

    public void setMaxAge(Long maxAge) {
        this.corsProp.setMaxAge(maxAge);
    }

    public String getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    public CorsConfiguration getConfig() {
        return corsProp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("urlMapping", urlMapping)
                .append("allowCredentials", corsProp.getAllowCredentials())
                .append("allowedHeaders", corsProp.getAllowedHeaders())
                .append("allowedMethods", allowedMethods)
                .append("allowedOrigins", corsProp.getAllowedOrigins())
                .append("maxAge", corsProp.getMaxAge())
                .toString();
    }
}
