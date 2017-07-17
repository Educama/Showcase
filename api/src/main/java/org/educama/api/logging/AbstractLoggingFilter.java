package org.educama.api.logging;

import com.netflix.zuul.ZuulFilter;

/**
 * Abstract logging filter.
 */
public abstract class AbstractLoggingFilter extends ZuulFilter {

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
