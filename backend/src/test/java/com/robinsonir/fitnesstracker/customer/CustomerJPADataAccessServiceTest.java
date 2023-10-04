package com.robinsonir.fitnesstracker.customer;

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

    private CustomerJPADataAccessService customerService;

    @MockBean
    private CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        customerService = new CustomerJPADataAccessService(customerRepository);
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John", "john@example.com", "password", 30, Gender.MALE),
                new Customer(2, "Alice", "alice@example.com", "password", 25, Gender.FEMALE)
        );

        // Mock the findAll method of the customerRepository
        Page<Customer> page = new PageImpl<>(mockCustomers, Pageable.unpaged(), mockCustomers.size());
        when(customerRepository.findAll(Pageable.ofSize(1000))).thenReturn(page);
    }

    @Test
    void selectAllCustomers() {
        // Call the selectAllCustomers method
        List<Customer> customers = customerService.selectAllCustomers();

        // Verify that the correct number of customers is returned
        assertEquals(2, customers.size());

    }

    @Test
    void selectCustomerById() {
        // Mock the behavior of the customerRepository's findById method
        int customerId = 1;
        Customer expectedCustomer = new Customer(customerId, "John", "john@example.com", "password", 30, Gender.MALE);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        // Call the selectCustomerById method
        Optional<Customer> result = customerService.selectCustomerById(customerId);

        // Verify that the expected customer is returned
        assertEquals(expectedCustomer, result.orElse(null));
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
        Integer customerIdToCheck = 1;
        when(customerRepository.existsCustomerById(customerIdToCheck)).thenReturn(true);

        // Call the existsCustomerById method
        boolean exists = customerService.existsCustomerById(customerIdToCheck);

        // Verify that a customer with the given ID exists
        assertTrue(exists);
    }

    @Test
    void deleteCustomerById() {
        // Mock the behavior of the customerRepository's deleteById method
        Integer customerIdToDelete = 1;
        when(customerRepository.existsById(customerIdToDelete)).thenReturn(true);

        // Call the deleteCustomerById method
        customerService.deleteCustomerById(customerIdToDelete);

        // Verify that the deleteById method was called with the correct customer ID
        verify(customerRepository).deleteById(customerIdToDelete);
    }

    @Test
    void updateCustomer() {
        Customer customerToUpdate = new Customer(1, "John", "john@example.com", "password", 30, Gender.MALE);

        // Mock the behavior of the customerRepository's save method
        when(customerRepository.save(customerToUpdate)).thenReturn(customerToUpdate);

        // Call the updateCustomer method
        customerService.updateCustomer(customerToUpdate);

        // Verify that the save method was called with the correct customer
        verify(customerRepository).save(customerToUpdate);
    }

    @Test
    void selectCustomerByUsername() {
        // Define a test email to search for
        String testEmail = "john@example.com";

        // Create a mock customer to return
        Customer mockCustomer = new Customer(1, "John", testEmail, "password", 30, Gender.MALE);

        // Mock the behavior of the customerRepository's findCustomerByEmail method
        when(customerRepository.findCustomerByEmail(testEmail)).thenReturn(Optional.of(mockCustomer));

        // Call the selectCustomerByUsername method
        Optional<Customer> selectedCustomer = customerService.selectCustomerByUsername(testEmail);

        // Verify that the expected customer is returned
        assertEquals(mockCustomer, selectedCustomer.orElse(null));
    }

    @Test
    void updateCustomerProfileImageId() {
        // Define a test profile image ID and customer ID
        String testProfileImageId = "profile123";
        Integer testCustomerId = 1;

        // Call the updateCustomerProfileImageId method
        customerService.updateCustomerProfileImageId(testProfileImageId, testCustomerId);

        // Verify that the updateProfileImageId method of customerRepository was called with the expected arguments
        verify(customerRepository).updateProfileImageId(testProfileImageId, testCustomerId);
    }
}