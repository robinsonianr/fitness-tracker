package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerDTOMapper implements Function<CustomerEntity, CustomerDTO> {

    @Override
    public CustomerDTO apply(CustomerEntity customerEntity) {
        return new CustomerDTO(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getGender(),
                customerEntity.getAge(),
                customerEntity.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                customerEntity.getUsername(),
                customerEntity.getProfileImageId()
        );
    }
}
