package dev.practice.QRCodeGenerator.controller;

import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.service.UserService;
import dev.practice.QRCodeGenerator.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/findall")
    public ResponseEntity<List<CustomUser>> findAll(){
        log.info(UserController.class.getName() + " : start finding all Users");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<List<CustomUser>> findUser(
            @PathVariable(name = "name") String name){
        log.info(UserController.class.getName() + " : start finding User " + name);
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestBody CustomUserDTO customUserDTO) throws Exception {

        log.info(UserController.class.getName() + " : start adding User " +
                customUserDTO.getFirstName() + " " + customUserDTO.getLastName());
        CustomUser user = userService.addUser(customUserDTO);

        log.info(UserController.class.getName() + " : start making QRCode of : " +
                customUserDTO.getFirstName() + " " + customUserDTO.getLastName());
        QRCodeGenerator.generateQRCode(user);

        return new ResponseEntity<>(
                user + " is created at " + LocalDateTime.now(),
                HttpStatus.CREATED);
    }
}
