package nacos.demo.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("PARK_CODE", HttpContextUtil.getHttpServletRequest().getHeader("PARK_CODE"));
    }
}
