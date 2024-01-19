package dev.practice.QRCodeGenerator.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BrowserController {

    @GetMapping("/home")
    public String home(){
        return "/grayscale/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/grayscale/login";
    }
}
