package nacos.demo.controller;

import nacos.demo.feign.UserClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    UserClient client;

    @GetMapping("/user/{id}")
    public Object show(@PathVariable("id") int id) {
        System.out.println("调用了");
        return id;
    }

    @GetMapping("/test")
    public Object test() {
        return client.getUserById(1);
    }
}