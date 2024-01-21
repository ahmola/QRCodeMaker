package dev.practice.QRCodeGenerator.controller.web;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class BrowserController {

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/home")
    public String home(){
        return "/grayscale/index";
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/login")
    public String login(){
        return "/grayscale/login";
    }
}
