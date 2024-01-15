package dev.practice.QRCodeGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QrCodeDTO {

    private String username;
    private String password;
    private String message;

}
