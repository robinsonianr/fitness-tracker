package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface CustomerRepository
        extends JpaRepository<CustomerEntity, Long> {
    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);

    Optional<CustomerEntity> findCustomerByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE CustomerEntity c SET c.profileImageId = ?1 WHERE c.id = ?2")
    void updateProfileImageId(String profileImageId, Long customerId);
}
