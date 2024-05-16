package com.example.vpn.utils;

import com.example.vpn.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private static final Long EXPIRATION_TIME = 86400000L;

    public JwtUtils() {
        String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));

        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .claim("id", user.getUid())
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, User user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 30)) //refreshToken có thời gian trong 30 ngày
                .signWith(secretKey)
                .compact();
    }

    public String generateFreeKey(String username) {
        return Jwts.builder()
                .claim("premium_type", "F")
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String generatePremiumKey(String username, Integer expire_time) {
        String premium_type = "";
        if (expire_time.equals(Constants.ONE_MONTH)) {
            premium_type = "A";
        }
        else if (expire_time.equals(Constants.SIX_MONTH)) {
            premium_type = "B";
        }
        else if (expire_time.equals(Constants.ONE_YEAR)) {
            premium_type = "C";
        } else if (expire_time.equals(Constants.TWO_YEAR)) {
            premium_type = "D";
        }
        else {
            premium_type = "F";
        }
        return Jwts.builder()
                .subject(username)
                .claim("premium_type", premium_type)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * expire_time))
                .signWith(secretKey)
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Integer extractUid(String token) {
        return extractClaims(token, claims -> claims.get("id", Integer.class));
    }

    public String extractPremiumType(String token) {
        return extractClaims(token, claims -> claims.get("premium_type", String.class));
    }

    //kiểm tra xem accessToken còn trong thời gian sử dụng không
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    //kiểm tra xem accessToken còn hợp lệ hay không
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

}
