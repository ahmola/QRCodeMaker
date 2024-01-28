package dev.practice.QRCodeGenerator.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.practice.QRCodeGenerator.dto.CustomUserDTO;
import dev.practice.QRCodeGenerator.dto.RegisterUserDTO;
import dev.practice.QRCodeGenerator.utils.CustomUserEntitySerializer;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


// do not Use Lombok!!! when using @OneToMany or other Relationships. Especially @ToString annotation
@JsonSerialize(using = CustomUserEntitySerializer.class)
@NoArgsConstructor
@Entity(name = "user")
public class CustomUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<QRCode> qrCodes;

    public CustomUser(CustomUserDTO customUserDTO){
        this.firstName = customUserDTO.getFirstName();
        this.lastName = customUserDTO.getLastName();
        this.email = customUserDTO.getEmail();
        this.password = customUserDTO.getPassword();
        this.phoneNumber = customUserDTO.getPhoneNumber();
        this.role = customUserDTO.getRole();
    }

    public CustomUser(RegisterUserDTO registerUserDTO){
        this.firstName = registerUserDTO.getFirstName();
        this.lastName = registerUserDTO.getLastName();
        this.email = registerUserDTO.getEmail();
        this.password = registerUserDTO.getPassword();
        this.role = Role.ROLE_USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.toString()));
    }

    public Long getId(){
        return this.id;
    }

    public String getRole(){
        return this.role.toString();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    @Override
    public String getUsername() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public List<QRCode> getQrCodes(){
        return this.qrCodes;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUser)) return false;
        CustomUser user = (CustomUser) o;
        return getId().equals(user.getId()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getPhoneNumber(), user.getPhoneNumber()) && getRole() == user.getRole() && Objects.equals(getQrCodes(), user.getQrCodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPassword(), getPhoneNumber(), getRole(), getQrCodes());
    }
}
