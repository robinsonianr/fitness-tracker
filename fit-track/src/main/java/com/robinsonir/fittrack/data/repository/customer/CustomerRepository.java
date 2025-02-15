package com.robinsonir.fittrack.data.repository.customer;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerUpdateRepository {

    @Query("select c from CustomerEntity c")
    List<CustomerEntity> findAllCustomers();

    @Query("select c from CustomerEntity c where c.id = ?1")
    Optional<CustomerEntity> findCustomerById(Long id);

    @Query("select c.email from CustomerEntity c where c.email = ?1")
    Optional<CustomerEntity> findCustomerByEmail(String email);

    @Query("select c from CustomerEntity c where c.email = ?1")
    Optional<CustomerEntity> findCustomerByUsername(String email);

    boolean existsByEmail(String email);
}
