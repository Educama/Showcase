package org.educama.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configuration properties for CORS related settings.
 */
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private boolean allowCredentials = true;

    @NotEmpty(message = "AllowedHeaders must not be empty")
    private List<String> allowedHeaders = Arrays.asList(CrossOrigin.DEFAULT_ALLOWED_HEADERS);

    @NotEmpty(message = "AllowedMethods must not be empty")
    private List<String> allowedMethods = Collections.singletonList(CorsConfiguration.ALL);

    @NotEmpty(message = "AllowedOrigins must not be empty")
    private List<String> allowedOrigins = Arrays.asList(CrossOrigin.DEFAULT_ORIGINS);

    @NotNull(message = "MaxAge must be set")
    private Long maxAge = CrossOrigin.DEFAULT_MAX_AGE;

    @NotEmpty(message = "UrlMapping must not be empty")
    private String urlMapping = "/**";

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public String getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("urlMapping", urlMapping)
                .append("allowCredentials", allowCredentials)
                .append("allowedHeaders", allowedHeaders)
                .append("allowedMethods", allowedMethods)
                .append("allowedOrigins", allowedOrigins)
                .append("maxAge", maxAge)
                .toString();
    }
}
