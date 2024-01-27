package dev.practice.QRCodeGenerator.repository;

import dev.practice.QRCodeGenerator.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {

}
