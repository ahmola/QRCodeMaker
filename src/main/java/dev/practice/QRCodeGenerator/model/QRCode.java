package dev.practice.QRCodeGenerator.model;

import com.fasterxml.jackson.annotation.*;
import dev.practice.QRCodeGenerator.utils.PathConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.nio.file.Path;
import java.sql.Timestamp;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
@Data
@Entity
public class QRCode {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Convert(converter = PathConverter.class)
    private Path path;
    public QRCode(Path path){
        this.path = path;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private CustomUser customUser;

    @CreationTimestamp
    private Timestamp createTime;

}
