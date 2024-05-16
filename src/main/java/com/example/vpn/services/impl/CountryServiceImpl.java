package com.example.vpn.services.impl;

import com.example.vpn.entities.Country;
import com.example.vpn.repositories.CountryRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Override
    public ResponseEntity<Object> getAllCountry() {
        List<Country> allCountry = countryRepository.findAll();

        return DataResponse.dataResponseBuilder(true, "OK", HttpStatus.OK, allCountry);
    }

    @Override
    public ResponseEntity<Object> getCountryById(Integer id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);

        if (optionalCountry.isEmpty()) {
            return OtherResponse.errorResponseBuilder(HttpStatus.NOT_FOUND, "Can't found country");
        }
        Country country = optionalCountry.get();
        return DataResponse.dataResponseBuilder(true, "OK", HttpStatus.OK, country);
    }
}