package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("select c from CustomerEntity c")
    List<CustomerEntity> findAllCustomers();

    @Query("select c from CustomerEntity c where c.id = ?1")
    Optional<CustomerEntity> findCustomerById(Long id);

    @Query("select c.email from CustomerEntity c where c.email = ?1")
    Optional<CustomerEntity> findCustomerByEmail(String email);

    @Query("select c from CustomerEntity c where c.email = ?1")
    Optional<CustomerEntity> findCustomerByUsername(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update CustomerEntity c set c.profileImageId = ?1 where c.id = ?2")
    void updateProfileImageId(String profileImageId, Long customerId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update CustomerEntity cust set cust.name = :name, cust.email = :email, cust.age = :age," +
            " cust.gender = :gender, cust.weight = :weight, cust.height = :height, cust.weightGoal = :weightGoal," +
            " cust.activity = :activity, cust.bodyFat = :bodyFat where cust.id = :id")
    void updateCustomer(Long id,
                        String name,
                        String email,
                        Integer age,
                        Gender gender,
                        Integer weight,
                        Integer height,
                        Integer weightGoal,
                        String activity,
                        Integer bodyFat
    );

}
