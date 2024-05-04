package com.example.vpn.repositories;

import com.example.vpn.entities.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, String> {

    @Query("SELECT v FROM VerifyCode v WHERE v.email = :email AND v.code = :code")
    VerifyCode check(String email, String code);

}
