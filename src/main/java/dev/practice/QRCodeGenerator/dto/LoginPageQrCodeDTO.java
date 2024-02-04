package dev.practice.QRCodeGenerator.dto;

public record LoginPageQrCodeDTO(
        String qrCodeName,
        String qrCodeMessage,
        String createdTime
) {
}
