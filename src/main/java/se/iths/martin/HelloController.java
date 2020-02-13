package se.iths.martin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class HelloController {

    @RequestMapping("/hello")
    public Greeting sayHello(){
        return new Greeting(1,"Hello");
    }
}
