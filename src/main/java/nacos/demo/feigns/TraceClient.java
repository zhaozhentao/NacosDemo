package nacos.demo.feigns;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface TraceClient {

    @RequestMapping(value = "/api/trace", method = RequestMethod.POST)
    String trace(@RequestBody List<String> services);
}
