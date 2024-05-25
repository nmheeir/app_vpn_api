package com.example.vpn.services;

import com.example.vpn.entities.Country;
import org.springframework.http.ResponseEntity;

public interface CountryService {
    ResponseEntity<Object> getAllCountry();
    ResponseEntity<Object> getCountryById(Integer id);

    ResponseEntity<Object> deleteCountryById(Integer id);

    ResponseEntity<Object> addCountry(Country country);

    ResponseEntity<Object> getPremiumCountry(Boolean isPremium);
}
