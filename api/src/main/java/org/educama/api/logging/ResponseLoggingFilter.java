package org.educama.api.logging;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * Filter to log responses.
 */
public class ResponseLoggingFilter extends AbstractLoggingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String headers = response.getHeaderNames()
            .stream()
            .map(headername -> headername + "=" + response.getHeader(headername))
            .collect(Collectors.joining("; "));
        LOGGER.info(String.format("Response for %s request to %s has status %s and headers %s", request.getMethod(),
            request.getRequestURL().toString(), response.getStatus(), headers));
        return null;
    }
}
