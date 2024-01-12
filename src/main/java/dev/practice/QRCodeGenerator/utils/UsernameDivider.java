package dev.practice.QRCodeGenerator.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.constructor.ConstructorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UsernameDivider {

    public static List<String> divideUsername(String name) throws UsernameNotFoundException{
        log.info("Start Divide firstName and lastName : " + name);
        List<String> fullName = new ArrayList<>(Arrays.stream(name.split(" ")).toList());

        if(fullName.size() != 2)
            throw new UsernameNotFoundException(
                    "Name cannot be Divided. Check if there is blank between firstName and lastName");

        log.info("FirstName : " + fullName.get(0) + " LastName : " + fullName.get(1));

        return fullName;
    }
}
