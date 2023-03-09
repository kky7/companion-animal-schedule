package org.backend.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.backend.constant.TokenProperties;
import org.springframework.beans.factory.annotation.Value;
import org.backend.repository.RefreshTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret.key}")
    private String secretKey;

    Key key;

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String email, String type){
        Date date = new Date();
        int validTime = type.equals(TokenProperties.AUTH_HEADER) ? TokenProperties.ACCESS_TOKEN_VALID_TIME : TokenProperties.REFRESH_TOKEN_VALID_TIME;

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

}
