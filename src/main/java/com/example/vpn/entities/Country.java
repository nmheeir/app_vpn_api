package com.example.vpn.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_name")
    private String name;

    @Column(name = "flag")
    private String flag;

    @Column(name = "config")
    private String config;

    @Column(name = "premium")
    private Boolean premium;

    @Column(name = "vpn_name")
    private String vpnName;

    @Column(name = "vpn_password")
    private String vpnPassword;

    public Country() {

    }

    public Country(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
