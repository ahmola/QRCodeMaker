package dev.practice.QRCodeGenerator.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForUtils;
import dev.practice.QRCodeGenerator.dto.AnonymousQrCodeDTO;
import dev.practice.QRCodeGenerator.model.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    public static Path generateAnonymousQRCode(AnonymousQrCodeDTO content) throws Exception{
        try {
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

            Path path = FileSystems.getDefault().getPath(qrCodePath + qrCodeName);
            log.info(QRCodeGenerator.class.getName() + " Generate Path : " + path);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            log.info(QRCodeGenerator.class.getName() + " QRCode Created at " + path);

            return path;
        }catch (Exception e){
            throw new RuntimeException("Something goes wrong in generateAnonymousQRCode() with Error Message"
                    + e.getMessage());
        }
    }

    // title will be added too
    @LogForUtils
    public static Path generateQRCode(CustomUser customUser, String message) throws Exception {
        try{
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
            log.info(QRCodeGenerator.class.getName() + " : Save QRCode at " + path);

            return path;
        }catch (Exception e){
            throw new RuntimeException("Something goes wrong in generateQRCode()"
                    + " with Error Message " + e.getMessage());
        }
    }

    @LogForUtils
    public static String decodeQrCode(Path path) throws Exception{
        try {
            BufferedImage bufferedImage = ImageIO.read(path.toFile());
            log.info(QRCodeGenerator.class.getName() + " : find QRCode : " + !bufferedImage.toString().isEmpty());

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(bufferedImage)
            ));
            log.info(QRCodeGenerator.class.getName() +  " : translate into BinaryBitMap");

            String result = new MultiFormatReader().decode(binaryBitmap).toString();

            int startIndex = result.indexOf("Message") + "Message : ".length();
            int endIndex = result.indexOf('\n', startIndex);

            String resultString = result.substring(startIndex, endIndex);
            log.info(QRCodeGenerator.class.getName() + " : result : " + resultString);

            return resultString;
        }catch (RuntimeException e){
            throw new RuntimeException("Something goes wrong during Decoding QRCode... : " + e.getMessage());
        }

    }
}
