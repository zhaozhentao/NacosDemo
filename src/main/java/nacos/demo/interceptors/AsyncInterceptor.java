package nacos.demo.interceptors;

import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HeaderHolder;
import nacos.demo.util.TraceInfoHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AsyncInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var traceInfo = request.getHeader("TRACE_INFO");
        var parkCode = request.getHeader("PARK_CODE");

        HeaderHolder.set(parkCode);
        TraceInfoHolder.set(traceInfo);

        log.info("trace info {}", traceInfo);

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HeaderHolder.remove();

        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
