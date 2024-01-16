package dev.practice.QRCodeGenerator.controller.api;

import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import dev.practice.QRCodeGenerator.dto.QrCodeDTO;
import dev.practice.QRCodeGenerator.dto.RegisterUserDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.service.UserService;
import dev.practice.QRCodeGenerator.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/findall")
    public ResponseEntity<List<CustomUser>> findAll(){
        log.info(ApiController.class.getName() + " : gets Get Request of findAll");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<List<CustomUser>> findUser(
            @PathVariable(name = "name") String name) throws Exception {
        log.info(ApiController.class.getName() + " : gets Get Request of findUser " + name);
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestBody CustomUserDTO customUserDTO, String message) throws Exception {

        log.info(ApiController.class.getName() + " : gets Post Request of addUser " +
                customUserDTO.getFirstName() + " " + customUserDTO.getLastName());
        CustomUser user = userService.addUser(customUserDTO);
        if(!message.isEmpty())
            log.info(ApiController.class.getName() + " : also gets message " + message);

        log.info(ApiController.class.getName() + " : start making QRCode of : " +
                customUserDTO.getFirstName() + " " + customUserDTO.getLastName());
        QRCodeGenerator.generateQRCode(user, message);

        return new ResponseEntity<>(
                user + " is created at " + LocalDateTime.now(),
                HttpStatus.CREATED);
    }

    @PostMapping("/createqr")
    public ResponseEntity<String> createQRCode(@RequestBody QrCodeDTO qrCodeDTO) throws Exception {
        log.info(ApiController.class.getName() + " : gets Post Request of createQRCode " + qrCodeDTO.getMessage());

        CustomUser user = userService.findByName(qrCodeDTO.getUsername()).get(0);
        if(passwordEncoder.matches(qrCodeDTO.getPassword(), user.getPassword())){
            log.info(ApiController.class.getName() + " : " + qrCodeDTO.getUsername() + " is OK to access");

            QRCodeGenerator.generateQRCode(user, qrCodeDTO.getMessage());
            log.info("Generate QRCode");

            return new ResponseEntity<>("Success Create QRCode with Message!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(Model model,
                                               @ModelAttribute("registerUser")RegisterUserDTO registerUserDTO){

        return new ResponseEntity<>("Registered!", HttpStatus.CREATED);
    }

}
