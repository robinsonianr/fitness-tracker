package com.robinsonir.fitnesstracker.customer;

import com.robinsonir.fitnesstracker.exception.RequestValidationException;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import com.robinsonir.fitnesstracker.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();
    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private S3Service s3Service;
    private CustomerService customerTest;

    @BeforeEach
    void setUp() {
        customerTest = new CustomerService(customerDAO, customerDTOMapper, passwordEncoder, s3Service);
        customerTest.setS3Bucket("fitness-tracker-customers");
    }

    @Test
    void getAllCustomers() {
        // When
        customerTest.getAllCustomers();

        // Then
        verify(customerDAO).selectAllCustomers();
    }

    @Test
    void testGetCustomer() {
        // Arrange: Create a test customer instance and mock behavior.
        int id = 1;
        Customer testCustomer = new Customer(id, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(testCustomer));

        CustomerDTO expected = customerDTOMapper.apply(testCustomer);

        // When
        CustomerDTO actual = customerTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addCustomer() {
        // Arrange: Create a test customer registration request and mock behavior.
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                "John Doe",
                "johndoe@example.com",
                "password123",
                30,
                Gender.MALE
        );

        // Mock behavior for customerDAO.existsCustomerWithEmail to return false (no duplicate email).
        when(customerDAO.existsCustomerWithEmail(registrationRequest.email())).thenReturn(false);

        // Mock behavior for passwordEncoder.encode to return the hashed password.
        when(passwordEncoder.encode(registrationRequest.password())).thenReturn("hashedPassword");

        // Act: Add the customer.
        customerTest.addCustomer(registrationRequest);

        // Assert: Verify that the customerDAO.insertCustomer method is called with the expected customer.
        verify(customerDAO).insertCustomer(
                argThat(customer ->
                        customer.getName().equals(registrationRequest.name()) &&
                                customer.getEmail().equals(registrationRequest.email()) &&
                                customer.getPassword().equals("hashedPassword") && // Hashed password
                                customer.getAge().equals(registrationRequest.age()) &&
                                customer.getGender().equals(registrationRequest.gender())
                )
        );
    }

    @Test
    void deleteCustomerById() {
        // Arrange: Mock behavior for customerDAO.existsCustomerById to return true (customer exists).
        int customerId = 1;
        when(customerDAO.existsCustomerById(customerId)).thenReturn(true);

        // Act: Delete the customer.
        assertDoesNotThrow(() -> customerTest.deleteCustomerById(customerId));

        // Assert: Verify that customerDAO.deleteCustomerById is called with the correct ID.
        verify(customerDAO).deleteCustomerById(customerId);
    }

    @Test
    void deleteCustomerById_CustomerNotFound() {
        // Arrange: Mock behavior for customerDAO.existsCustomerById to return false (customer not found).
        int customerId = 1;
        when(customerDAO.existsCustomerById(customerId)).thenReturn(false);

        // Act and Assert: Deleting a non-existent customer should throw a ResourceNotFoundException.
        assertThrows(ResourceNotFoundException.class, () -> customerTest.deleteCustomerById(customerId));

        // Verify that customerDAO.deleteCustomerById is not called in this case.
        verify(customerDAO, never()).deleteCustomerById(customerId);
    }

    @Test
    void checkIfCustomerExistsOrThrow_CustomerExists() {
        // Arrange: Mock behavior for customerDAO.existsCustomerById to return true (customer exists).
        int customerId = 1;
        when(customerDAO.existsCustomerById(customerId)).thenReturn(true);

        // Act: Check if the customer exists.
        assertDoesNotThrow(() -> customerTest.checkIfCustomerExistsOrThrow(customerId));

        // Assert: No exception should be thrown.
    }

    @Test
    void checkIfCustomerExistsOrThrow_CustomerNotFound() {
        // Arrange: Mock behavior for customerDAO.existsCustomerById to return false (customer not found).
        int customerId = 1;
        when(customerDAO.existsCustomerById(customerId)).thenReturn(false);

        // Act and Assert: Checking for a non-existent customer should throw a ResourceNotFoundException.
        assertThrows(ResourceNotFoundException.class, () -> customerTest.checkIfCustomerExistsOrThrow(customerId));
    }

    @Test
    void updateCustomer_ValidChanges() {
        // Arrange: Create a test customer, mock behavior, and prepare the update request.
        int customerId = 1;
        Customer existingCustomer = new Customer(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Updated Name", "updatedemail@example.com", 35);

        // Act: Update the customer.
        assertDoesNotThrow(() -> customerTest.updateCustomer(customerId, updateRequest));

        // Assert: Verify that customerDAO.updateCustomer is called with the updated customer.
        verify(customerDAO).updateCustomer(
                argThat(updatedCustomer ->
                        updatedCustomer.getId().equals(customerId) &&
                                updatedCustomer.getName().equals(updateRequest.name()) &&
                                updatedCustomer.getEmail().equals(updateRequest.email()) &&
                                updatedCustomer.getAge().equals(updateRequest.age()) &&
                                // Other fields are not changed
                                updatedCustomer.getPassword().equals(existingCustomer.getPassword()) &&
                                updatedCustomer.getGender().equals(existingCustomer.getGender())
                )
        );
    }

    @Test
    void updateCustomer_NoChanges() {
        // Arrange: Create a test customer and mock behavior.
        int customerId = 1;
        Customer existingCustomer = new Customer(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, null);

        // Act and Assert: Updating with no changes should throw a RequestValidationException.
        assertThrows(RequestValidationException.class, () -> customerTest.updateCustomer(customerId, updateRequest));

        // Verify that customerDAO.updateCustomer is not called in this case.
        verify(customerDAO, never()).updateCustomer(any());
    }

    @Test
    void uploadCustomerProfilePicture_Success() {
        // Arrange: Create a test customer, mock behavior, and prepare a test file.
        int customerId = 1;
        when(customerDAO.existsCustomerById(customerId)).thenReturn(true);

        byte[] bytes = "Hello World".getBytes();

        MultipartFile testFile = new MockMultipartFile("file", bytes);


        String bucket = "fitness-tracker-customers";

        // Act: Upload the profile picture.
        customerTest.uploadCustomerProfilePicture(customerId, testFile);

        // Then
        ArgumentCaptor<String> profileImageIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(customerDAO).updateCustomerProfileImageId(profileImageIdArgumentCaptor.capture(), eq(customerId));

        // Assert: Verify that s3Service.putObject is called with the correct arguments.
        verify(s3Service).putObject(
                bucket, // Replace with the actual S3 bucket name
                "profile-images/%s/%s".formatted(customerId, profileImageIdArgumentCaptor.getValue()), // Replace with the expected S3 key
                bytes // Any byte array representing the file content
        );

    }


    @Test
    void getProfilePicture_CustomerExists() {
        // Arrange: Create a test customer and mock behavior.
        int customerId = 1;
        String profileImageId = "ca4cd8f6-3487-4e79-ba0f-56e8047d5a62";
        byte[] expectedImageData = "Hello World".getBytes();

        Customer testCustomer = new Customer(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        testCustomer.setProfileImageId(profileImageId);

        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(testCustomer));
        when(s3Service.getObject("fitness-tracker-customers", "profile-images/1/ca4cd8f6-3487-4e79-ba0f-56e8047d5a62"))
                .thenReturn(expectedImageData);

        // Act: Get the profile picture.
        byte[] actualImageData = customerTest.getProfilePicture(customerId);

        // Then
        assertThat(actualImageData).isEqualTo(expectedImageData);
    }

    @Test
    void getProfilePicture_CustomerDoesNotExist() {
        // Arrange: Mock behavior for a customer that does not exist.
        int customerId = 1;
        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.empty());

        // Act and Assert: Ensure that a ResourceNotFoundException is thrown.
        assertThatThrownBy(() -> customerTest.getProfilePicture(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [1] not found");
    }

    @Test
    void getProfilePicture_CustomerHasNoProfileImage() {
        // Arrange: Create a test customer with no profile image.
        int customerId = 1;
        Customer testCustomer = new Customer(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(testCustomer));

        // Act and Assert: Ensure that a ResourceNotFoundException is thrown.
        assertThatThrownBy(() -> customerTest.getProfilePicture(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [1] profile image not found");
    }
}