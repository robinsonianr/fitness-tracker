package com.robinsonir.fitnesstracker.data;

import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDAO;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDAO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class JPADataAccessService implements CustomerDAO, WorkoutDAO {

    private final CustomerRepository customerRepository;
    private final WorkoutRepository workoutRepository;

    public JPADataAccessService(CustomerRepository customerRepository, WorkoutRepository workoutRepository) {
        this.customerRepository = customerRepository;
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<CustomerEntity> selectAllCustomers() {
        Page<CustomerEntity> page = customerRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<CustomerEntity> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customerRepository.existsCustomerById(id);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void updateCustomer(CustomerEntity update) {
        customerRepository.save(update);
    }

    @Override
    public Optional<CustomerEntity> selectCustomerByUsername(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public void updateCustomerProfileImageId(String profileImageId, Long customerId) {
        customerRepository.updateProfileImageId(profileImageId, customerId);
    }

    @Override
    public List<WorkoutEntity> selectAllWorkouts() {
        Page<WorkoutEntity> page = workoutRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<WorkoutEntity> selectWorkoutById(Long id) {
        return workoutRepository.findWorkoutEntityById(id);
    }

    @Override
    public void insertWorkout(WorkoutEntity workoutEntity) {
        workoutRepository.save(workoutEntity);
    }

    @Override
    public boolean existsWorkoutEntityById(Long id) {
        return workoutRepository.existsWorkoutEntitiesById(id);
    }

    @Override
    public boolean existsWorkoutEntityByCustomer(CustomerEntity customer) {
        return workoutRepository.existsWorkoutEntityByCustomer(customer);
    }

    @Override
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }
}
