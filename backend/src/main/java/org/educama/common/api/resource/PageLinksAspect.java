package org.educama.common.api.resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * Implementation of the {@PageLinks} annotation to generate HATEOAS Links for paged list resources.
 */
@Component
@Aspect
public class PageLinksAspect {
    @Around("@annotation(org.educama.common.api.resource.PageLinks) && execution(org.springframework.hateoas.ResourceSupport+ *(..)) && args(page, ..)")
    public Object pageLinksAdvice(ProceedingJoinPoint joinPoint, Page<?> page) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        PageLinks pageLinks = method.getAnnotation(PageLinks.class);
        Class<?> controller = pageLinks.value();
        UriComponentsBuilder original = originalUri(controller, request);
        ResourceSupport resourceSupport = (ResourceSupport) joinPoint.proceed();
        if (page.hasNext()) {
            UriComponentsBuilder nextBuilder = replacePageParams(original, page.nextPageable());
            resourceSupport.add(new Link(nextBuilder.toUriString()).withRel("next"));
        }
        if (page.hasPrevious()) {
            UriComponentsBuilder prevBuilder = replacePageParams(original, page.previousPageable());
            resourceSupport.add(new Link(prevBuilder.toUriString()).withRel("prev"));
        }
        return resourceSupport;
    }

    private UriComponentsBuilder originalUri(Class<?> controller, HttpServletRequest request) {
        UriComponentsBuilder baseUri = ControllerLinkBuilder.linkTo(controller).toUriComponentsBuilder();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                baseUri.queryParam(entry.getKey(), value);
            }
        }
        return baseUri;
    }

    private UriComponentsBuilder replacePageParams(UriComponentsBuilder origional, Pageable pageable) {
        UriComponentsBuilder builder = origional.cloneBuilder();
        builder.replaceQueryParam("page", pageable.getPageNumber());
        builder.replaceQueryParam("size", pageable.getPageSize());
        return builder;
    }
}
