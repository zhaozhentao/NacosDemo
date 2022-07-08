package nacos.demo.controller;

import nacos.demo.feigns.UserClient;
import nacos.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class Controller {

    @Resource
    UserClient client;

    @Value("${text}")
    private String text;

    @GetMapping("/user/{id}")
    public Object show(@PathVariable("id") int id) {
        User user = new User("旺财", new Date());
        user.insert();
        return text;
    }

    @GetMapping("/test")
    public Object test() {
        return client.getUserById(1);
    }
}
