package ws.ivi.dyndns.netszatyor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";  // ez a hello.html nevű sablonra hivatkozik
    }
}
