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

    @Value("${qrcode.path}")
    private String qrCodePath;

    private final ResourceLoader resourceLoader;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/login")
    public String userLoginForm() {
        log.info(UserController.class.getName() + " return Login Page");

        return "newlogin";
    }

    @GetMapping("/qrcode")
    public String getQRCode(Model model) throws IOException {
        log.info(UserController.class.getName() + " get QRCode");

        Resource directoryResource = resourceLoader.getResource("file:" + qrCodePath);
        log.info(UserController.class.getName() + " : initiate " + directoryResource);

        File qrCodeDir = directoryResource.getFile();
        log.info(UserController.class.getName() + " : find Directory " + qrCodeDir.isDirectory() + " " + qrCodeDir);

        List<String> qrCodeNames = Arrays.asList(qrCodeDir.list());
        log.info(UserController.class.getName() + " : find Files " + qrCodeNames);

        model.addAttribute("qrCodeNames", qrCodeNames);

        return "qrcode";
    }
}
