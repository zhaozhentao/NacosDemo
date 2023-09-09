package nacos.demo.services;

import nacos.demo.feigns.UserClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    UserClient userClient;

    @Async("myAsync")
    public void asyncCall() {
        userClient.getUserById(false);
    }
}
