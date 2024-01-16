package dev.practice.QRCodeGenerator.controller.web;

import dev.practice.QRCodeGenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
