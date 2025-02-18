package com.robinsonir.fittrack.data.repository.customer;

import com.robinsonir.fittrack.data.Gender;

public interface CustomerUpdateRepository {


    void updateProfileImageId(String profileImageId, Long customerId);


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
