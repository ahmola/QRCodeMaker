package dev.practice.QRCodeGenerator.service;

import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<CustomUser> findAll(){
        log.info(UserService.class.getName() + " : starts finding all Users");

        return userRepository.findAll();
    }

    public CustomUser addUser(CustomUserDTO customUserDTO){
        log.info(UserService.class.getName() + " : starts adding User " + customUserDTO.toString());

        return userRepository.save(new CustomUser(customUserDTO));
    }

    public List<CustomUser> findByName(String name){
        log.info(UserService.class.getName() + " : starts find User " + name);

        List<String> fullName = Arrays.stream(name.split(" ")).toList();
        String firstName = fullName.get(0);
        String lastName = fullName.get(1);

        return userRepository.findByName(firstName, lastName);
    }
}
