package dev.practice.QRCodeGenerator.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    @GetMapping("/login")
    public String userLoginForm() {
        log.info(UserController.class.getName() + " Get Request of userLoginForm");

        return "newlogin";
    }

    @GetMapping("/register")
    public String registerUserForm(){
        log.info(this.getClass().getName() + " : Get Request of registerUserForm");

        return "register";
    }

    @GetMapping("/search")
    public String searchUserForm(){
        log.info(this.getClass().getName() + " : Get Request of searchUserForm");

        return "search";
    }
}
