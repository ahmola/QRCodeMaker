package dev.practice.QRCodeGenerator.model;

import com.fasterxml.jackson.annotation.*;
import dev.practice.QRCodeGenerator.utils.PathConverter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Objects;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "QRCode{" +
                "id=" + id +
                ", path=" + path +
                ", customUser=" + customUser +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QRCode)) return false;
        QRCode qrCode = (QRCode) o;
        return getId().equals(qrCode.getId()) && getPath().equals(qrCode.getPath()) && getCustomUser().equals(qrCode.getCustomUser()) && getCreateTime().equals(qrCode.getCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPath(), getCustomUser(), getCreateTime());
    }
}
