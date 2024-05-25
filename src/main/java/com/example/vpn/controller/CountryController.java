package com.example.vpn.controller;

import com.example.vpn.entities.Country;
import com.example.vpn.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(
            @PathVariable(value = "id") Integer id
    ) {
        return countryService.deleteCountryById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCountry(
            @RequestBody Country country
    ) {
        return countryService.addCountry(country);
    }

    @GetMapping("/preCountry")
    public ResponseEntity<Object> getPremiumCountry(
            @RequestParam(name = "isPremium") Boolean isPremium
    ) {
        return countryService.getPremiumCountry(isPremium);
    }
}
