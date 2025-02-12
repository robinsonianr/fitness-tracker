package com.robinsonir.fittrack.data.service.customer;

import com.robinsonir.fittrack.data.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDataService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDataService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    boolean existsByEmail(String email) {
      return customerRepository.existsByEmail(email);
    }

    boolean existsByCustomerId(Long id) {
        return customerRepository.existsById(id);
    }
}
