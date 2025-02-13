package com.robinsonir.fittrack.security.auth;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.repository.customer.Customer;
import com.robinsonir.fittrack.mappers.CustomerMapper;
import com.robinsonir.fittrack.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final CustomerMapper customerMapper;

    private final JwtTokenUtil jwtUtil;


    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),
                        request.password()));
        CustomerEntity principal = (CustomerEntity) authentication.getPrincipal();
        Customer customer = customerMapper.customerEntityToCustomer(principal);
        var token = jwtUtil.generateToken(customer.username(), customer.roles());
        return new AuthResponse(token, customer);
    }
}
