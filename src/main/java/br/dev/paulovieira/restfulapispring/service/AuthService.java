package br.dev.paulovieira.restfulapispring.service;

import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.security.dto.*;
import br.dev.paulovieira.restfulapispring.security.jwt.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.logging.*;

@Service
public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsDto data) {
        LOGGER.log(Level.INFO, "Authenticating user {0}", data.getUsername());

        var username = data.getUsername();
        var password = data.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);
            var tokenResponse = new TokenDto();

            if (user.isPresent()) {
                LOGGER.log(Level.INFO, "Creating token for user {0}", username);
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.get().getRoles());
            } else {
                LOGGER.log(Level.INFO, "User {0} not found", username);
                throw new UsernameNotFoundException("User not found");
            }

            LOGGER.log(Level.INFO, "Token created for user {0}", username);
            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            LOGGER.log(Level.INFO, "Invalid username/password supplied for user {0}", username);
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        LOGGER.log(Level.INFO, "Refreshing token for user {0}", username);


        var user = userRepository.findByUsername(username);
        var tokenResponse = new TokenDto();

        if (user.isPresent()) {
            LOGGER.log(Level.INFO, "Creating token for user {0}", username);
            tokenResponse = jwtTokenProvider.createRefreshToken(refreshToken);
        } else {
            LOGGER.log(Level.INFO, "User {0} not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        LOGGER.log(Level.INFO, "Token created for user {0}", username);
        return ResponseEntity.ok(tokenResponse);

    }
}
