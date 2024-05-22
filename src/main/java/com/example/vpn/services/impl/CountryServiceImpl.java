package com.example.vpn.services.impl;

import com.example.vpn.entities.Country;
import com.example.vpn.repositories.CountryRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public ResponseEntity<Object> deleteCountryById(Integer id) {
        try {
            countryRepository.deleteById(id);
            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Delete Country Successfully");
        } catch (EmptyResultDataAccessException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.NOT_FOUND, "Country not found with id: " + id);
        } catch (DataAccessException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while trying to delete the country");
        }
    }

    @Override
    public ResponseEntity<Object> addCountry(Country country) {
        try {
            Country savedCountry = countryRepository.save(country);
            return OtherResponse.successResponseBuilder(HttpStatus.CREATED, "Country added successfully with id: " + savedCountry.getId());
        } catch (DataAccessException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while trying to add the country");
        }
    }
}