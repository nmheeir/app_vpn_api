package com.example.vpn.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "verify_code")
@Data
public class VerifyCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "expire")
    private String expire;

    @Column(name = "email")
    private String email;

    public VerifyCode() {

    }

    public VerifyCode(String code, String expire, String email) {
        this.code = code;
        this.expire = expire;
        this.email = email;
    }
}
