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

    public Country() {

    }

    public Country(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
