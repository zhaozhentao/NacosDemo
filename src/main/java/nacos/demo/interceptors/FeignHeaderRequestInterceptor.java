package nacos.demo.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HeaderHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("here");
        requestTemplate.header("PARK_CODE", HeaderHolder.get());
    }
}
