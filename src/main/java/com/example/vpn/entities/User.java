package com.example.vpn.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "verify_at")
    private String verifyAt;

    @Column(name = "premium_key")
    private String premiumKey;

    public User(String username, String email, String password, String role, String premiumKey) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.premiumKey = premiumKey;
    }

    public User() {

    }

}
