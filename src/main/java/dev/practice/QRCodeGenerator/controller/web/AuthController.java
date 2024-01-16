package dev.practice.QRCodeGenerator.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@Controller
public class AuthController {

    @Value("${qrcode.path}")
    private String qrCodePath;

    private final ResourceLoader resourceLoader;

    @GetMapping("/qrcode")
    public String getQRCodeForm(Model model) throws IOException {
        log.info(this.getClass().getName() + " get QRCode");

        Resource directoryResource = resourceLoader.getResource("file:" + qrCodePath);
        log.info(this.getClass().getName() + " : initiate " + directoryResource);

        File qrCodeDir = directoryResource.getFile();
        log.info(this.getClass().getName() + " : find Directory " + qrCodeDir.isDirectory() + " " + qrCodeDir);

        List<String> qrCodeNames = Arrays.asList(qrCodeDir.list());
        log.info(this.getClass().getName() + " : find Files " + qrCodeNames);

        model.addAttribute("qrCodeNames", qrCodeNames);

        return "qrcode";
    }
}
