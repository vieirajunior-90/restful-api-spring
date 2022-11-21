package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.security.dto.*;
import br.dev.paulovieira.restfulapispring.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint", description = "Authentication API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Authenticates a user and returns a JWT token")
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody AccountCredentialsDto data) {
        if (checkCredentialParamsForSignIn(data)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = authService.signin(data);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Refreshes a JWT token for an authenticated user")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<Object> refreshToken(@PathVariable ("username") String username,
                                               @RequestHeader ("Authorization") String refreshToken) {
        if (checkParamsForRefreshToken(username, refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = authService.refreshToken(username, refreshToken);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return ResponseEntity.ok(token);
    }

    private static boolean checkParamsForRefreshToken(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isEmpty() || refreshToken.isBlank()
                || username == null || username.isEmpty() || username.isBlank();
    }

    private static boolean checkCredentialParamsForSignIn(AccountCredentialsDto data) {
        return data == null || data.getUsername() == null || data.getPassword() == null ||
                data.getUsername().isEmpty() || data.getPassword().isEmpty() ||
                data.getUsername().isBlank() || data.getPassword().isBlank();
    }
}
