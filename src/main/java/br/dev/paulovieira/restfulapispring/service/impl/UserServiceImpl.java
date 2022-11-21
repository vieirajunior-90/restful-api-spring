package br.dev.paulovieira.restfulapispring.service.impl;

import br.dev.paulovieira.restfulapispring.repository.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.logging.*;

@Service
public class UserServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.log(Level.INFO, "Finding user by username: {0}", username);

        var user = repository.findByUsername(username);

        if (user.isEmpty()) {
            LOGGER.log(Level.INFO, "User not found: {0}", username);
            throw new UsernameNotFoundException("User not found");
        }

        LOGGER.log(Level.INFO, "User found: {0}", username);
        return user.get();
    }
}
