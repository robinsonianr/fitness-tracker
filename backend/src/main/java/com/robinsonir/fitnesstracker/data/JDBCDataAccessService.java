package com.robinsonir.fitnesstracker.data;

import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDAO;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRowMapper;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDAO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class JDBCDataAccessService implements CustomerDAO, WorkoutDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;
    private final WorkoutRowMapper workoutRowMapper;

    public JDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                 WorkoutRowMapper workoutRowMapper,
                                 CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
        this.workoutRowMapper = workoutRowMapper;
    }

    @Override
    public List<CustomerEntity> selectAllCustomers() {
        var sql = """
                SELECT c.id, c.password, c.name, c.email, c.age, c.gender, c.profile_image_id,
                w.id as workout_id,  w.workout_type, w.calories, w.duration_minutes
                FROM fit_tracker.customer c
                LEFT JOIN fit_tracker.workout w ON c.id = w.customer_id
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<CustomerEntity> selectCustomerById(Long id) {
        var sql = """
                SELECT c.id, c.password, c.name, c.email, c.age, c.gender, c.profile_image_id,
                w.id as workout_id,  w.workout_type, w.calories, w.duration_minutes
                FROM fit_tracker.customer c
                LEFT JOIN fit_tracker.workout w ON c.id = w.customer_id
                WHERE c.id = ?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(CustomerEntity customerEntity) {
        var sql = """
                INSERT INTO fit_tracker.customer (
                name,
                email,
                password,
                age,
                gender)
                VALUES (?, ?, ?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getPassword(),
                customerEntity.getAge(),
                customerEntity.getGender().name()
        );

        System.out.println("insertCustomer result: " + result + " row affected");
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM fit_tracker.customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerById(Long id) {
        var sql = """
                SELECT count(id)
                FROM fit_tracker.customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        var sql = """
                DELETE
                FROM fit_tracker.customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, customerId);
        System.out.println("deleteCustomerById result: " + result + " row affected");
    }

    @Override
    public void updateCustomer(CustomerEntity update) {
        if (update.getName() != null) {
            String sql = "UPDATE fit_tracker.customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId()
            );
            System.out.println("update customerEntity name result: " + result + " row affected");
        }
        if (update.getAge() != null) {
            String sql = "UPDATE fit_tracker.customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getAge(),
                    update.getId()
            );
            System.out.println("update customerEntity age result: " + result + " row affected");
        }
        if (update.getEmail() != null) {
            String sql = "UPDATE fit_tracker.customer SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId());
            System.out.println("update customerEntity email result: " + result + " row affected");
        }
    }

    @Override
    public Optional<CustomerEntity> selectCustomerByUsername(String email) {
        var sql = """
                SELECT id, name, email, password, age, gender, profile_image_id
                FROM fit_tracker.customer
                WHERE email = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, email)
                .stream()
                .findFirst();
    }

    @Override
    public void updateCustomerProfileImageId(String profileImageId, Long customerId) {
        var sql = """
                UPDATE fit_tracker.customer
                SET profile_image_id = ?
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, profileImageId, customerId);
        System.out.println("updateCustomerProfileImageId result: " + result + " row affected");
    }
    @Override
    public List<WorkoutEntity> selectAllWorkouts() {
        var sql = """
                SELECT id, customer_id, workout_type, calories, duration_minutes FROM fit_tracker.workout
                """;

        return jdbcTemplate.query(sql, workoutRowMapper);
    }

    @Override
    public Optional<WorkoutEntity> selectWorkoutById(Long id) {
        var sql = """
                SELECT *
                FROM fit_tracker.workout
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, workoutRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertWorkout(WorkoutEntity workoutEntity) {
        var sql = """
                INSERT INTO fit_tracker.workout (
                customer_id,
                workout_type,
                calories,
                duration_minutes,
                workout_date)
                VALUES (?, ?, ?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                workoutEntity.getCustomer().getId(),
                workoutEntity.getWorkoutType(),
                workoutEntity.getCalories(),
                workoutEntity.getDurationMinutes(),
                workoutEntity.getWorkoutDate()

        );
        System.out.println("insertWorkout result: " + result + " row affected");
    }

    @Override
    public boolean existsWorkoutEntityById(Long id) {
        var sql = """
                SELECT count(id)
                FROM fit_tracker.workout
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsWorkoutEntityByCustomer(CustomerEntity customer) {
        var sql = """
                SELECT count(id)
                FROM fit_tracker.workout
                WHERE customer = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customer);
        return count != null && count > 0;
    }


    @Override
    public void deleteWorkoutById(Long id) {
        var sql = """
                DELETE
                FROM fit_tracker.workout
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteCustomerById result: " + result + " row affected");
    }
}
