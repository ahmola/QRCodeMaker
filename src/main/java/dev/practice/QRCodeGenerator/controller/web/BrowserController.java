package dev.practice.QRCodeGenerator.controller.web;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import dev.practice.QRCodeGenerator.dto.AnonymousQrCodeDTO;
import dev.practice.QRCodeGenerator.dto.RegisterUserDTO;
import dev.practice.QRCodeGenerator.utils.QRCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class BrowserController {

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("registerUserDTO", new RegisterUserDTO());
        model.addAttribute("content", new AnonymousQrCodeDTO());
        return "/grayscale/index";
    }

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/anonymousgenerate")
    public String anonymousGenerate(
            @ModelAttribute("content")AnonymousQrCodeDTO content,
            RedirectAttributes redirectAttributes) throws Exception {
        try {
            QRCodeGenerator.generateAnonymousQRCode(content);
            redirectAttributes.addFlashAttribute("isGenerated", true);
            log.info(BrowserController.class.getName() + " : Generate " + content);

            return "redirect:/home";
        }catch (RuntimeException e){
            throw new Exception("Something goes wrong during Generating AnonymousQRCode : " + e.getMessage());
        }
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/login")
    public String login(){
        return "/grayscale/login";
    }

}
