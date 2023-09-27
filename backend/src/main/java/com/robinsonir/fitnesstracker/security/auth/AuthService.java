package com.robinsonir.fitnesstracker.security.auth;

import com.robinsonir.fitnesstracker.customer.Customer;
import com.robinsonir.fitnesstracker.customer.CustomerDTO;
import com.robinsonir.fitnesstracker.customer.CustomerDTOMapper;
import com.robinsonir.fitnesstracker.security.jwt.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDTOMapper customerDTOMapper;

    private final JwtTokenUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       CustomerDTOMapper customerDTOMapper,
                       JwtTokenUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerDTOMapper = customerDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),
                        request.password()));
        Customer principal = (Customer) authentication.getPrincipal();
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);
        var token = jwtUtil.generateToken(customerDTO.username(), customerDTO.roles());
        return new AuthResponse(token, customerDTO);
    }
}
