package pl.sda.hello.controllers;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(basePath = "/", value = "/", description = "Redirect Controller")
@Controller
public class RedirectController {

    @RequestMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }

}