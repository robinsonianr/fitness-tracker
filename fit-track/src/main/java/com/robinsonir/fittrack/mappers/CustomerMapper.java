package com.robinsonir.fittrack.mappers;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.repository.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = FitTrackMapperConfig.class, uses = {WorkoutMapper.class})
public interface CustomerMapper {

    @Mapping(target = "workouts", source = "customerWorkouts")
    @Mapping(target = "roles", source = "authorities")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    @Mapping(target = "workouts", source = "customerWorkouts")
    @Mapping(target = "roles", source = "authorities")
    List<Customer> customerEntityListToCustomerList(List<CustomerEntity> customerEntityList);


    default List<String> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities == null ? List.of() :
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
    }

}
