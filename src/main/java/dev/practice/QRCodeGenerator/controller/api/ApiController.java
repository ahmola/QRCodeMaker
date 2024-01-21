package dev.practice.QRCodeGenerator.controller.api;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
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

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/findall")
    public ResponseEntity<List<CustomUser>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.FOUND);
    }

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/find/{name}")
    public ResponseEntity<List<CustomUser>> findUser(
            @PathVariable(name = "name") String name) throws Exception {
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.FOUND);
    }

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestBody CustomUserDTO customUserDTO, String message) throws Exception {

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

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/createqr")
    public ResponseEntity<String> createQRCode(@RequestBody QrCodeDTO qrCodeDTO) throws Exception {

        CustomUser user = userService.findByName(qrCodeDTO.getUsername()).get(0);
        if(passwordEncoder.matches(qrCodeDTO.getPassword(), user.getPassword())){
            log.info(ApiController.class.getName() + " : " + qrCodeDTO.getUsername() + " is OK to access");

            QRCodeGenerator.generateQRCode(user, qrCodeDTO.getMessage());
            log.info("Generate QRCode");

            return new ResponseEntity<>("Success Create QRCode with Message!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(Model model,
                                               @ModelAttribute("registerUser")RegisterUserDTO registerUserDTO){


        return new ResponseEntity<>("Registered!", HttpStatus.CREATED);
    }

}
