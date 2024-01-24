package dev.practice.QRCodeGenerator.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForUtils;
import dev.practice.QRCodeGenerator.dto.AnonymousQrCodeDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Slf4j
@Component
public class QRCodeGenerator {

    private static String qrCodePath;

    @Value("${qrcode.path}")
    private void setPath(String path){
        qrCodePath = path;
    }

    @LogForUtils
    public static void generateAnonymousQRCode(AnonymousQrCodeDTO content) throws Exception{
        String qrCodeName = qrCodePath.hashCode() +
                "Annonymous" +
                content.getMessage().hashCode() +
                "-QRCODE.png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                "Name: Anonymous\n\n" +
                        "Message : " + content.getMessage() + "\n\n" +
                        "Generated at : " + LocalDateTime.now(),
                BarcodeFormat.QR_CODE,
                200, 200);

        log.info("Generate Anonymous QRCode Complete with Message : " + content.getMessage());

        /*
        * Have to add user's QRCode Repository!
        * */
        log.info(QRCodeGenerator.class.getName()+" Send to User : " + content.getReceiverName());

        Path path = FileSystems.getDefault().getPath(qrCodePath+qrCodeName);
        log.info(QRCodeGenerator.class.getName() + " Generate Path : " + path);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    // title will be added too
    @LogForUtils
    public static void generateQRCode(CustomUser customUser, String message) throws Exception {

        String qrCodeName = qrCodePath.hashCode() +
                customUser.getFirstName() +
                customUser.getId() +
                message.hashCode() +
                "-QRCODE.png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                        "Name: " + customUser.getFirstName() + " " + customUser.getLastName() + "\n" +
                        "E-mail: " + customUser.getEmail() + "\n\n" +
                        "Message : " + message + "\n\n" +
                        "Generated at : " + LocalDateTime.now(),
                BarcodeFormat.QR_CODE,
                200, 200);
        log.info(QRCodeGenerator.class.getName() + " : Generate QRCode " + bitMatrix);

        Path path = FileSystems.getDefault().getPath(qrCodePath+qrCodeName);
        log.info(QRCodeGenerator.class.getName() + " : Generate Path " + path);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
