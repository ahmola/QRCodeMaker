package dev.practice.QRCodeGenerator.controller.web;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/login")
    public String userLoginForm() {
        return "newlogin";
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/register")
    public String registerUserForm(){
        log.info(this.getClass().getName() + " : Get Request of registerUserForm");

        return "register";
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/search")
    public String searchUserForm(){
        log.info(this.getClass().getName() + " : Get Request of searchUserForm");

        return "search";
    }
}
