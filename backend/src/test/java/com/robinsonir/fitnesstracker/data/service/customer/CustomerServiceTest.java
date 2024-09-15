package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTO;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTOMapper;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerDataService customerDataService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private S3Service s3Service;


    private CustomerService customerTest;

    @BeforeEach
    void setUp() {
        customerTest = new CustomerService(customerDTOMapper, passwordEncoder, s3Service, customerRepository, customerDataService);
        customerTest.setS3Bucket("fitness-tracker-customers");
    }

    @Test
    void getAllCustomers() {
        // When
        customerTest.getAllCustomers();

        // Then
        verify(customerRepository).findAllCustomers();
    }

    @Test
    void testGetCustomer() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity customerEntity = new CustomerEntity(
                "John Doe",
                "john.doe@example.com",
                "password123",
                30,
                Gender.MALE
        );
        customerEntity.setId(customerId);

        // Create and save a workout
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(customerEntity);
        workout.setWorkoutType("Rowing");
        workout.setCalories(550);
        workout.setDurationMinutes(90);
        workout.setWorkoutDate(OffsetDateTime.now());

        customerEntity.setCustomerWorkouts(List.of(workout));

        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(customerEntity));

        // Act
        CustomerDTO customerDTO = customerTest.getCustomer(customerId);

        // Assert
        assertEquals(customerId, customerDTO.id());
        assertEquals("John Doe", customerDTO.name());
        assertEquals("john.doe@example.com", customerDTO.email());
        assertEquals(30, customerDTO.age());
        assertEquals(Gender.MALE, customerDTO.gender());
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

        // Mock behavior for passwordEncoder.encode to return the hashed password.
        when(passwordEncoder.encode(registrationRequest.password())).thenReturn("hashedPassword");

        // Act: Add the customer.
        customerTest.addCustomer(registrationRequest);

        // Assert: Verify that the customerDAO.insertCustomer method is called with the expected customer.
        verify(customerRepository).save(
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
    void checkIfCustomerExistsOrThrowCustomerExists() {
        // Arrange: Mock behavior for customerRepository.existById to return true (customer exists).
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // Act: Check if the customer exists.
        assertDoesNotThrow(() -> customerTest.checkIfCustomerExistsOrThrow(customerId));

        // Assert: No exception should be thrown.
    }

    @Test
    void checkIfCustomerExistsOrThrowCustomerNotFound() {
        // Arrange: Mock behavior for customerRepository.existById to return false (customer not found).
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Act and Assert: Checking for a non-existent customer should throw a ResourceNotFoundException.
        assertThrows(ResourceNotFoundException.class, () -> customerTest.checkIfCustomerExistsOrThrow(customerId));
    }

    @Test
    void testUpdateCustomerWithChanges() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity existingCustomer = new CustomerEntity(customerId, "John Doe", "john@example.com", "hashedPassword", 30, Gender.MALE);

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Jane Doe",             // Updated name
                "jane@example.com",     // Updated email
                28,                     // Updated age
                Gender.FEMALE,          // Updated gender
                65,                     // Updated weight
                170,                    // Updated height
                60,                     // Updated weight goal
                "Cycling",              // Updated activity
                18                      // Updated body fat
        );

        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(updateRequest.email())).thenReturn(false);

        // Act
        customerTest.updateCustomer(customerId, updateRequest);

        // Assert
        verify(customerRepository).updateCustomer(customerId,
                updateRequest.name(),
                updateRequest.email(),
                updateRequest.age(),
                updateRequest.gender(),
                updateRequest.weight(),
                updateRequest.height(),
                updateRequest.weightGoal(),
                updateRequest.activity(),
                updateRequest.bodyFat()
        );
    }

    @Test
    void testUpdateCustomerNoChanges() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity existingCustomer = new CustomerEntity(customerId, "John Doe", "john@example.com", "hashedPassword", 30, Gender.MALE);

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, // No change in name
                null, // No change in email
                null, // No change in age
                null, // No change in gender
                null, // No change in weight
                null, // No change in height
                null, // No change in weight goal
                null, // No change in activity
                null  // No change in body fat
        );

        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Act and Assert
        assertThrows(RequestValidationException.class, () -> customerTest.updateCustomer(customerId, updateRequest));
    }

    @Test
    void uploadCustomerProfilePicture() {
        // Arrange: Create a test customer, mock behavior, and prepare a test file.
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        byte[] bytes = "Hello World".getBytes();

        MultipartFile testFile = new MockMultipartFile("file", bytes);


        String bucket = "fitness-tracker-customers";

        // Act: Upload the profile picture.
        customerTest.uploadCustomerProfilePicture(customerId, testFile);

        // Then
        ArgumentCaptor<String> profileImageIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(customerRepository).updateProfileImageId(profileImageIdArgumentCaptor.capture(), eq(customerId));

        // Assert: Verify that s3Service.putObject is called with the correct arguments.
        verify(s3Service).putObject(
                bucket, // Replace with the actual S3 bucket name
                "profile-images/%s/%s".formatted(customerId, profileImageIdArgumentCaptor.getValue()), // Replace with the expected S3 key
                bytes // Any byte array representing the file content
        );

    }


    @Test
    void getProfilePictureCustomerExists() {
        // Arrange: Create a test customer and mock behavior.
        Long customerId = 1L;
        String profileImageId = "ca4cd8f6-3487-4e79-ba0f-56e8047d5a62";
        byte[] expectedImageData = "Hello World".getBytes();
        // Create and save a workout


        CustomerEntity testCustomerEntity = new CustomerEntity(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        testCustomerEntity.setProfileImageId(profileImageId);
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(testCustomerEntity);
        workout.setWorkoutType("Rowing");
        workout.setCalories(550);
        workout.setDurationMinutes(90);
        workout.setWorkoutDate(OffsetDateTime.now());
        testCustomerEntity.setCustomerWorkouts(List.of(workout));

        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(testCustomerEntity));
        when(s3Service.getObject("fitness-tracker-customers", "profile-images/1/ca4cd8f6-3487-4e79-ba0f-56e8047d5a62"))
                .thenReturn(expectedImageData);

        // Act: Get the profile picture.
        byte[] actualImageData = customerTest.getProfilePicture(customerId);

        // Then
        assertThat(actualImageData).isEqualTo(expectedImageData);
    }

    @Test
    void getProfilePictureCustomerDoesNotExist() {
        // Arrange: Mock behavior for a customer that does not exist.
        Long customerId = 1L;
        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.empty());

        // Act and Assert: Ensure that a ResourceNotFoundException is thrown.
        assertThatThrownBy(() -> customerTest.getProfilePicture(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [1] not found");
    }

    @Test
    void getProfilePictureCustomerHasNoProfileImage() {
        // Arrange: Create a test customer with no profile image.
        Long customerId = 1L;
        CustomerEntity testCustomerEntity = new CustomerEntity(customerId, "John Doe", "johndoe@example.com", "hashedPassword", 30, Gender.MALE);
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(testCustomerEntity);
        workout.setWorkoutType("Rowing");
        workout.setCalories(550);
        workout.setDurationMinutes(90);
        workout.setWorkoutDate(OffsetDateTime.now());
        testCustomerEntity.setCustomerWorkouts(List.of(workout));

        when(customerRepository.findCustomerById(customerId)).thenReturn(Optional.of(testCustomerEntity));

        // Act and Assert: Ensure that a ResourceNotFoundException is thrown.
        assertThatThrownBy(() -> customerTest.getProfilePicture(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [1] profile image not found");
    }
}