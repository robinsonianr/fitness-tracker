package com.robinsonir.fitnesstracker.data.service.customer;

public record CustomerUpdateRequest(
      String name,
      String email,
      Integer age
) {
}
