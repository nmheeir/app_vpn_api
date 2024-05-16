package com.example.vpn.controller;

import com.example.vpn.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<Object> allCountry() {
        return countryService.getAllCountry();
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getCountryById(
            @RequestParam(value = "id") Integer id
    ) {
        return countryService.getCountryById(id);
    }
}
