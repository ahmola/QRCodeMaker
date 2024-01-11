package dev.practice.QRCodeGenerator.model;

import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "user")
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    public CustomUser(CustomUserDTO customUserDTO){
        this.firstName = customUserDTO.getFirstName();
        this.lastName = customUserDTO.getLastName();
        this.email = customUserDTO.getEmail();
        this.phoneNumber = customUserDTO.getPhoneNumber();
    }
}
