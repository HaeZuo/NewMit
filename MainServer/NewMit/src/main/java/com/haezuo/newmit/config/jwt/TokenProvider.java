package com.haezuo.newmit.config.jwt;

import com.haezuo.newmit.login.domain.User;
import com.haezuo.newmit.common.constants.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // 토큰 생성 메서드
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 토큰 생성을 위한 내부 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim(userInfo.KEY_USER_ID, user.getId())
                .claim(userInfo.KEY_USER_NM, user.getMbNm())
                .claim(userInfo.KEY_USER_MAIL, user.getEmail())
                .claim(userInfo.KEY_USER_ROLE, user.getMbRole())
                .claim(userInfo.KEY_USER_O_AUTH_PROVIDER, user.getOauthProvider())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 토큰 유효성 검사 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰을 통한 사용자 인증 정보 가져오기
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject
                (), "", authorities), token, authorities);
    }

    // 토큰에서 사용자 아이디 가져오기
    public Long getUserId(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (Exception e) {
            claims = null;
        }

        return claims.get(userInfo.KEY_USER_ID, Long.class);
    }

    // 토큰에서 사용자 아이디 가져오기
    public String getUserNm(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (Exception e) {
            return null;
        }

        return claims.get(userInfo.KEY_USER_NM, String.class);
    }

    public String getUserMail(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (Exception e) {
            return null;
        }

        return claims.get(userInfo.KEY_USER_MAIL, String.class);
    }

    public String getUserRole(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (Exception e) {
            return null;
        }

        return claims.get(userInfo.KEY_USER_ROLE, String.class);
    }

    public String getUserOAuthProvider(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (Exception e) {
            return null;
        }

        return claims.get(userInfo.KEY_USER_O_AUTH_PROVIDER, String.class);
    }

    // 토큰에서 클레임 가져오기
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
