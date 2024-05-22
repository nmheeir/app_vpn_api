package com.example.vpn.repositories;

import com.example.vpn.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CountryRepository extends JpaRepository<Country, Integer> {
    void deleteCountryById();
}
