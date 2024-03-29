package dev.practice.QRCodeGenerator.controller.web;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import dev.practice.QRCodeGenerator.controller.api.ApiController;
import dev.practice.QRCodeGenerator.dto.AnonymousQrCodeDTO;
import dev.practice.QRCodeGenerator.dto.LoginPageQrCodeDTO;
import dev.practice.QRCodeGenerator.dto.RegisterUserDTO;
import dev.practice.QRCodeGenerator.dto.SendQrCodeDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.model.QRCode;
import dev.practice.QRCodeGenerator.service.UserService;
import dev.practice.QRCodeGenerator.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BrowserController {

    private final UserService userService;

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/home")
    public String home(Model model, Principal principal){
        if(principal == null)
            model.addAttribute("isLogin", false);
        else
            model.addAttribute("isLogin", true);
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
            Path path = QRCodeGenerator.generateAnonymousQRCode(content);
            userService.addQRCodesByUsername(content.getReceiverName(), List.of(path));

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

    @LogForController(Request =  RequestMethod.POST)
    @PostMapping("/register")
    public String register(@ModelAttribute(name = "registerUserDTO")RegisterUserDTO registerUserDTO,
                           RedirectAttributes redirectAttributes) throws Exception{
        try {
            userService.addUser(registerUserDTO);
            log.info(BrowserController.class.getName() + " : Successfully add User : " + registerUserDTO.getFirstName());
            redirectAttributes.addFlashAttribute("isRegistered", true);
        }catch (Exception e){
            throw new RuntimeException("Something goes wrong... " + e.getMessage());
        }

        return "redirect:/home";
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/user")
    public String user(Model model, Principal principal) throws Exception {

        model.addAttribute("content", new AnonymousQrCodeDTO());

        CustomUser user = userService.findByName(principal.getName()).get(0);

        // have to add qrcode decoder to show details
        List<LoginPageQrCodeDTO> userQrCodes = new ArrayList<>();
        for (QRCode qrcode : user.getQrCodes()){
            LoginPageQrCodeDTO userQrCode = new LoginPageQrCodeDTO(
                    qrcode.getPath().getFileName().toString(),
                    QRCodeGenerator.decodeQrCode(qrcode.getPath()),
                    qrcode.getCreateTime().toString());
            userQrCodes.add(userQrCode);
        }

        log.info(BrowserController.class.getName() + " : Send " +
                    user.getUsername() + " with : " + userQrCodes);
        model.addAttribute("QRCodes", userQrCodes);

        return "/grayscale/user";
    }

}
