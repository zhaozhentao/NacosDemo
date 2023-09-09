package nacos.demo.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.util.HttpContextUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("get PARK_CODE {}", "sdf");

        requestTemplate.header("PARK_CODE", HttpContextUtil.getHttpServletRequest().getHeader("PARK_CODE"));
    }
}
