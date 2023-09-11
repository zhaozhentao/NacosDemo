package nacos.demo.interceptors;

import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HeaderHolder;
import nacos.demo.util.TraceInfoHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AsyncInterceptor implements AsyncHandlerInterceptor {

    @Value("${spring.application.name}")
    String service;

    @Resource
    Registration registration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var parkCode = request.getHeader("PARK_CODE");
        HeaderHolder.set(parkCode);

        var traceInfo = request.getHeader("TRACE_INFO");
        if (traceInfo != null) {
            traceInfo += ";";
        }
        traceInfo += service + ":" + registration.getMetadata().get("gray");
        TraceInfoHolder.set(traceInfo);

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HeaderHolder.remove();

        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
