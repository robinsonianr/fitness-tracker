package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<CustomerEntity> selectAllCustomers();

    Optional<CustomerEntity> selectCustomerById(Long id);

    void insertCustomer(CustomerEntity customerEntity);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerById(Long id);

    void deleteCustomerById(Long customerId);

    void updateCustomer(CustomerEntity update);

    Optional<CustomerEntity> selectCustomerByUsername(String email);

    void updateCustomerProfileImageId(String profileImageId, Long customerId);
}
