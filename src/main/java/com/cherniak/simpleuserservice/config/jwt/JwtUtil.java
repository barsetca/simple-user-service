package com.cherniak.simpleuserservice.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.lifetime}")
    private long tokenLifetime;

    //todo When make second service, change Algorithm.HMAC256 to RSA256
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plusMillis(tokenLifetime))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String extractUsernameByToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthoritiesByToken(String token) {
        Claim rolesClaim = JWT.decode(token).getClaim("roles");
        if (rolesClaim.isNull()) {
            return List.of();
        }
        return rolesClaim.asList(String.class).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("Invalid auth token: ...{} ", token.substring(token.length() - 4));
            return false;
        }
    }
}
