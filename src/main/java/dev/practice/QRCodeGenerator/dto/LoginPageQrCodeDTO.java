package dev.practice.QRCodeGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public record LoginPageQrCodeDTO(
        String qrCodeName,
        String createdTime
) {
}
