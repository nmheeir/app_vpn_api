package com.example.vpn.services;

import org.springframework.http.ResponseEntity;

public interface CountryService {
    ResponseEntity<Object> getAllCountry();
}
