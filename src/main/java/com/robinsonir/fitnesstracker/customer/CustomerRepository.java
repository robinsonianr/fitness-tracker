package com.robinsonir.fitnesstracker.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {
    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Integer id);

    Optional<Customer> findCustomerByUsername(String email);
}
