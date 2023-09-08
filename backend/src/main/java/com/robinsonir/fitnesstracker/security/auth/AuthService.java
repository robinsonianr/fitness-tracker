package com.robinsonir.fitnesstracker.security.auth;

import com.robinsonir.fitnesstracker.customer.Customer;
import com.robinsonir.fitnesstracker.security.jwt.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),
                        request.password()));
        Customer customer = (Customer) authentication.getPrincipal();
        var token = jwtUtil.generateToken(customer.getUsername());
        return new AuthResponse(token);
    }
}
