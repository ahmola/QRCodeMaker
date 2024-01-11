package dev.practice.QRCodeGenerator.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import dev.practice.QRCodeGenerator.model.CustomUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class QRCodeGenerator {

    private static String qrCodePath;

    @Value("${qrcode.path}")
    private void setPath(String path){
        qrCodePath = path;
    }

    public static void generateQRCode(CustomUser customUser) throws Exception {
        String qrCodeName = qrCodePath.hashCode() +
                customUser.getFirstName() +
                customUser.getId() +
                "-QRCODE.png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                "ID: " + customUser.getId() + "\n" +
                        "FirstName: " + customUser.getFirstName() + "\n" +
                        "LastName: " + customUser.getLastName() + "\n" +
                        "PhoneNumber: " + customUser.getPhoneNumber() + "\n",
                BarcodeFormat.QR_CODE,
                200, 200);

        Path path = FileSystems.getDefault().getPath(qrCodePath+qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
