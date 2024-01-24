package dev.practice.QRCodeGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnonymousQrCodeDTO {

    String receiverName;

    String message;
}
