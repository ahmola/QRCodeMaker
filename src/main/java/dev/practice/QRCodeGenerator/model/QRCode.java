package dev.practice.QRCodeGenerator.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.nio.file.Path;
import java.sql.Timestamp;

@Data
@Entity
public class QRCode {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private final Path path;
    public QRCode(Path path){
        this.path = path;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser customUser;

    @CreationTimestamp
    private Timestamp createTime;



}
