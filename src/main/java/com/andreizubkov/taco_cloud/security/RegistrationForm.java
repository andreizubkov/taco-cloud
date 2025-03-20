package com.andreizubkov.taco_cloud.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationForm {
    
    @NotBlank(message="Username is required")
    private String username;
    
    @NotBlank
    @Size(min=5)
    private String password;

    private String confirm;

    @NotBlank(message="Fullname is required")
    private String fullname;

    @NotBlank(message="Street is required")
    private String street;

    @NotBlank(message="City is required")
    private String city;

    @NotBlank(message="State is required")
    private String state;

    @NotBlank(message="Zip code is required")
    private String zip;

    @Digits(integer=11, fraction=0)
    @Size(min=11)
    private String phoneNumber;

    public Users toUser(PasswordEncoder passwordEncoder) {
        return new Users(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phoneNumber);
    }
}
