package nacos.demo.controller;

import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.UserClient;
import nacos.demo.services.TestService;
import nacos.demo.util.HttpContextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

@Slf4j
@RestController
public class TestController {

    @Resource
    UserClient userClient;

    @Resource
    TestService testService;

    @GetMapping("/api/qw")
    public Callable<String> test() {
        return () -> userClient.getUserById(false);
    }

    @GetMapping("/user")
    public String user() {
        log.info("header {}", HttpContextUtil.getHttpServletRequest().getHeader("PARK_CODE"));
        return "hao";
    }

    @GetMapping("/api/async_service")
    public String async() {
        testService.asyncCall();

        return "haha";
    }
}
