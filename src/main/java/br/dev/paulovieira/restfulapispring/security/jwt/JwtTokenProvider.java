package br.dev.paulovieira.restfulapispring.security.jwt;

import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.security.dto.*;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;
import jakarta.annotation.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.web.servlet.support.*;

import java.time.*;
import java.util.*;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private Long validationInMilliseconds = 3600000L; // 1h

    private final UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDto createAccessToken(String username, List<String> roles) {
        Instant now = Instant.now();
        Instant validation = now.plusMillis(validationInMilliseconds);

        // Creating access token
        var accessToken = getAccessToken(username, roles, now, validation);

        // Creating refresh token
        var refreshToken = getRefreshToken(username, roles, now);

        return new TokenDto(username, true, now, validation, accessToken, refreshToken);
    }

    // Creating refresh token
    public TokenDto createRefreshToken(String refreshToken) {

        // Remove Bearer from token
        if (refreshToken.contains("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(refreshToken);

        var username = decodedJWT.getSubject();
        var roles = decodedJWT.getClaim("roles").asList(String.class);

        return createAccessToken(username, roles);
    }

    private String getAccessToken(String username, List<String> roles, Instant now, Instant validation) {
        // Get the url from server
        var issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(validation))
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm);
    }

    private String getRefreshToken(String username, List<String> roles, Instant now) {
        // Recalculate the validation time
        var validationRefreshToken = now.plusMillis(validationInMilliseconds * 2);

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(validationRefreshToken))
                .withSubject(username)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        var decodedJWT = decodeToken(token);
        try {
            return !decodedJWT.getExpiresAt().before(Date.from(Instant.now()));
        } catch (JWTVerificationException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}
