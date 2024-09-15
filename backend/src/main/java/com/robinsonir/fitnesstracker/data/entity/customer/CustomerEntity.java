package com.robinsonir.fitnesstracker.data.entity.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.AbstractEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "fit_tracker", name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity extends AbstractEntity implements UserDetails {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_image_id", unique = true)
    private String profileImageId;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight_goal")
    private Integer weightGoal;

    @Column(name = "activity")
    private String activity;

    @Column(name = "body_fat")
    private Integer bodyFat;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WorkoutEntity> customerWorkouts;

    public CustomerEntity(Long id, String name, String email, String password, int age, Gender gender, String profileImageId) {
        setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.profileImageId = profileImageId;
    }

    public CustomerEntity(Long id, String name, String email, String password, Integer age, Gender gender) {
        setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;

    }

    public CustomerEntity(String name,
                          String email,
                          String password,
                          Integer age,
                          Gender gender,
                          Integer weight,
                          Integer height,
                          Integer weightGoal,
                          String activity,
                          Integer bodyFat) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.weightGoal = weightGoal;
        this.activity = activity;
        this.bodyFat = bodyFat;

    }

    public CustomerEntity(
            String name,
            String email,
            String password,
            Integer age,
            Gender gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
