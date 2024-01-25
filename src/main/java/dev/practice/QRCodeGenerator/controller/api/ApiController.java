package dev.practice.QRCodeGenerator.controller.api;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import dev.practice.QRCodeGenerator.dto.AnonymousQrCodeDTO;
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

import java.nio.file.Path;
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

    @LogForController(Request = RequestMethod.GET)
    @GetMapping("/find/{name}/qrcode")
    public ResponseEntity<List<String>> findUserQRCode(@PathVariable(name = "name") String name) throws Exception{
        List<String> qrCodes =  userService.findByName(name).get(0).getQrCodes();
        log.info("name's QRCode : " + qrCodes);
        return new ResponseEntity<>(qrCodes, HttpStatus.FOUND);
    }

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestBody CustomUserDTO customUserDTO, String message) throws Exception {

        if(!message.isEmpty())
            log.info(ApiController.class.getName() + " : also gets message " + message);

        CustomUser user = new CustomUser(customUserDTO);
        log.info(ApiController.class.getName() + " : start making QRCode of : " +
                customUserDTO.getFirstName() + " " + customUserDTO.getLastName());
        Path path = QRCodeGenerator.generateQRCode(user, message);

        log.info(ApiController.class.getName() + " : save QRCode in UserRepository in " + path);
        userService.addUser(customUserDTO, List.of(path.toString()));

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

            Path path = QRCodeGenerator.generateQRCode(user, qrCodeDTO.getMessage());
            log.info("Generate QRCode");

            userService.addQRCodesByUsername(qrCodeDTO.getUsername(), List.of(path.toString()));
            log.info("Add Path to UserRepository");

            return new ResponseEntity<>("Success Create QRCode with Message!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @LogForController(Request = RequestMethod.POST)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute("registerUserDTO")RegisterUserDTO registerUserDTO)
            throws Exception {
        try {
            userService.addUser(registerUserDTO);
            log.info(ApiController.class.getName() + " : Successfully add User : " + registerUserDTO.getFirstName());
        }catch (Exception e){
            throw new Exception("Something wrong... : " + e.getMessage());
        }

        return new ResponseEntity<>("Registered!", HttpStatus.CREATED);
    }

    @LogForController(Request =  RequestMethod.POST)
    @PostMapping("/anonymousgenerate")
    public ResponseEntity<String> anonymousGenerate(@ModelAttribute("content")AnonymousQrCodeDTO content) {
        try {
            Path path = QRCodeGenerator.generateAnonymousQRCode(content);
            log.info(ApiController.class.getName() + " : Generate QRCode Successfully");

            userService.addQRCodesByUsername(content.getReceiverName(), List.of(path.toString()));
            log.info(ApiController.class.getName() + " : add QRCode to User : " + content.getReceiverName());

            return new ResponseEntity<>("Generated!", HttpStatus.CREATED);
        }catch (Exception e){
            throw new RuntimeException("Error occurred while Generate AnonymousQRCode " + e.getMessage());
        }
    }
}
