package nacos.demo.interceptors;

import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HeaderHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AsyncInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HeaderHolder.set(request.getHeader("PARK_CODE"));

        log.info("save header to ttl");

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HeaderHolder.remove();

        log.info("remove header from ttl");

        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
