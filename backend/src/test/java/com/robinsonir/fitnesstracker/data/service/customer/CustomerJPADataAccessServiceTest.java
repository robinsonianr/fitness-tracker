package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.JPADataAccessService;
import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerJPADataAccessServiceTest {

    private JPADataAccessService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @BeforeEach
    void setUp() {
        customerService = new JPADataAccessService(customerRepository, workoutRepository);
        List<CustomerEntity> mockCustomerEntities = List.of(
                new CustomerEntity(1L, "John", "john@example.com", "password", 30, Gender.MALE),
                new CustomerEntity(2L, "Alice", "alice@example.com", "password", 25, Gender.FEMALE)
        );

        // Mock the findAll method of the customerRepository
        Page<CustomerEntity> page = new PageImpl<>(mockCustomerEntities, Pageable.unpaged(), mockCustomerEntities.size());
        when(customerRepository.findAll(Pageable.ofSize(1000))).thenReturn(page);
    }

    @Test
    void selectAllCustomers() {
        // Call the selectAllCustomers method
        List<CustomerEntity> customerEntities = customerService.selectAllCustomers();

        // Verify that the correct number of customers is returned
        assertEquals(2, customerEntities.size());

    }

    @Test
    void selectCustomerById() {
        // Mock the behavior of the customerRepository's findById method
        Long customerId = 1L;
        CustomerEntity expectedCustomerEntity = new CustomerEntity(customerId, "John", "john@example.com", "password", 30, Gender.MALE);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomerEntity));

        // Call the selectCustomerById method
        Optional<CustomerEntity> result = customerService.selectCustomerById(customerId);

        // Verify that the expected customer is returned
        assertEquals(expectedCustomerEntity, result.orElse(null));
    }

    @Test
    void existsCustomerWithEmail() {
        // Mock the behavior of the customerRepository's existsCustomerByEmail method
        String emailToCheck = "john@example.com";
        when(customerRepository.existsCustomerByEmail(emailToCheck)).thenReturn(true);

        // Call the existsCustomerWithEmail method
        boolean exists = customerService.existsCustomerWithEmail(emailToCheck);

        // Verify that a customer with the given email exists
        assertTrue(exists);
    }

    @Test
    void existsCustomerById() {
        // Mock the behavior of the customerRepository's existsCustomerById method
        Long customerIdToCheck = 1L;
        when(customerRepository.existsCustomerById(customerIdToCheck)).thenReturn(true);

        // Call the existsCustomerById method
        boolean exists = customerService.existsCustomerById(customerIdToCheck);

        // Verify that a customer with the given ID exists
        assertTrue(exists);
    }

    @Test
    void deleteCustomerById() {
        // Mock the behavior of the customerRepository's deleteById method
        Long customerIdToDelete = 1L;
        when(customerRepository.existsById(customerIdToDelete)).thenReturn(true);

        // Call the deleteCustomerById method
        customerService.deleteCustomerById(customerIdToDelete);

        // Verify that the deleteById method was called with the correct customer ID
        verify(customerRepository).deleteById(customerIdToDelete);
    }

    @Test
    void updateCustomer() {
        CustomerEntity customerEntityToUpdate = new CustomerEntity(1L, "John", "john@example.com", "password", 30, Gender.MALE);

        // Mock the behavior of the customerRepository's save method
        when(customerRepository.save(customerEntityToUpdate)).thenReturn(customerEntityToUpdate);

        // Call the updateCustomer method
        customerService.updateCustomer(customerEntityToUpdate);

        // Verify that the save method was called with the correct customer
        verify(customerRepository).save(customerEntityToUpdate);
    }

    @Test
    void selectCustomerByUsername() {
        // Define a test email to search for
        String testEmail = "john@example.com";

        // Create a mock customer to return
        CustomerEntity mockCustomerEntity = new CustomerEntity(1L, "John", testEmail, "password", 30, Gender.MALE);

        // Mock the behavior of the customerRepository's findCustomerByEmail method
        when(customerRepository.findCustomerByEmail(testEmail)).thenReturn(Optional.of(mockCustomerEntity));

        // Call the selectCustomerByUsername method
        Optional<CustomerEntity> selectedCustomer = customerService.selectCustomerByUsername(testEmail);

        // Verify that the expected customer is returned
        assertEquals(mockCustomerEntity, selectedCustomer.orElse(null));
    }

    @Test
    void updateCustomerProfileImageId() {
        // Define a test profile image ID and customer ID
        String testProfileImageId = "profile123";
        Long testCustomerId = 1L;

        // Call the updateCustomerProfileImageId method
        customerService.updateCustomerProfileImageId(testProfileImageId, testCustomerId);

        // Verify that the updateProfileImageId method of customerRepository was called with the expected arguments
        verify(customerRepository).updateProfileImageId(testProfileImageId, testCustomerId);
    }
}