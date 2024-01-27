package dev.practice.QRCodeGenerator.service;

import dev.practice.QRCodeGenerator.model.CustomUser;
import dev.practice.QRCodeGenerator.model.QRCode;
import dev.practice.QRCodeGenerator.repository.QRCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QRCoderService {

    private final QRCodeRepository qrCodeRepository;

    public List<QRCode> findAll(){
        return qrCodeRepository.findAll();
    }

    public List<QRCode> save(List<Path> paths, CustomUser customUser){
        List<QRCode> qrCodes = new ArrayList<>();
        for (Path path : paths){
            QRCode qrCode = new QRCode(path);
            qrCode.setCustomUser(customUser);
            qrCodeRepository.save(qrCode);
            qrCodes.add(qrCode);
        }
        return qrCodes;
    }
}
