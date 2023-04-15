package com.robinsonir.fitnesstracker.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerById(Integer id);

    void deleteCustomerById(Integer customerId);

    void updateCustomer(Customer update);

    Optional<Customer> selectUserByEmail(String email);
}
