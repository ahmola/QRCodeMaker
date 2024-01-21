package dev.practice.QRCodeGenerator.service;

import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import dev.practice.QRCodeGenerator.dto.RegisterUserDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.repository.UserRepository;
import dev.practice.QRCodeGenerator.utils.UsernameDivider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.WrongClassException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<CustomUser> findAll(){
        log.info(UserService.class.getName() + " : starts finding all Users");

        return userRepository.findAll();
    }

    public CustomUser addUser(CustomUserDTO customUserDTO){
        log.info(UserService.class.getName() + " : starts adding User " + customUserDTO.toString());
        customUserDTO.setPassword(passwordEncoder.encode(customUserDTO.getPassword()));

        return userRepository.save(new CustomUser(customUserDTO));
    }

    public CustomUser addUser(RegisterUserDTO registerUserDTO){
        log.info(UserService.class.getName() + " : starts adding User " + registerUserDTO.toString());
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        return userRepository.save(new CustomUser(registerUserDTO));
    }

    public List<CustomUser> findByName(String name) throws Exception {
        log.info(UserService.class.getName() + " : starts find User with Name" + name);

        List<String> fullName = UsernameDivider.divideUsername(name);

        return userRepository.findByName(fullName.get(0), fullName.get(1));
    }

    public CustomUser findByEmail(String email) throws Exception{

        log.info(UserService.class.getName() + " : starts find User with Email : " + email);

        List<CustomUser> user = userRepository.findByEmail(email);

        if(user.isEmpty())
            throw new ClassNotFoundException("There is no such user : " + email);
        else if (user.size() > 1)
            throw new WrongClassException("User Duplication Found with : " + email, user.toString(), email);

        return user.get(0);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(UserService.class.getName() + " starts authenticate : " + email);

        CustomUser user = userRepository.findByEmail(email).get(0);
        log.info("Found User for Authentication : " + user);

        return user;
    }
}
