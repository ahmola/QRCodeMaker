package dev.practice.QRCodeGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUserDTO {

    private String email;

    private String password;

    private String reCheckPassword;
}
