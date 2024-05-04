package com.example.vpn.services.impl;

import com.example.vpn.entities.Country;
import com.example.vpn.repositories.CountryRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Override
    public ResponseEntity<Object> getAllCountry() {
        List<Country> allCountry = countryRepository.findAll();

        return DataResponse.dataResponseBuilder(true, "OK", HttpStatus.OK, allCountry);
    }
}
