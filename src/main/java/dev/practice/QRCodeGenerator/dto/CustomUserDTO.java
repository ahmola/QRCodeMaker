package dev.practice.QRCodeGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomUserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
