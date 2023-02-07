package com.springdemo.marmindemo.security;

import com.springdemo.marmindemo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {
    private static final String SECRET_KEY =
            "2a35949f783599e0702ee69e0acbb100e254dd414205ba9516275f2a0bc98bc7dc77abc9df071636420e0207a3a3aa0932f6ea25a3f9a1d3f9722343503cb5c1";
    public String create(UserEntity userEntity){
        //기한 지금으로부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );
        //JWT 토큰 생성
        return Jwts.builder()
                //headers에 들어갈 내용 및 서명을 하기 위한 Secret_key
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userEntity.getId())//sub토큰주인
                .setIssuer("demo app")//iss발행주체
                .setIssuedAt(new Date())//iat생성날짜
                .setExpiration(expiryDate)//exp만료날짜
                .compact();
    };

    public String validateAndGetUserId(String token){
        //parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        //위조되지 않았다면 페이로드(claims리턴), 위조라면 예외를 날림
        //그 중 우리는 userId가 필요하므로 getBody를 쓴다
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
