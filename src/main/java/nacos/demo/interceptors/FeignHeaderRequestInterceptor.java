package nacos.demo.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HeaderHolder;
import nacos.demo.util.TraceInfoHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("PARK_CODE", HeaderHolder.get());
        requestTemplate.header("TRACE_INFO", TraceInfoHolder.get());
    }
}
