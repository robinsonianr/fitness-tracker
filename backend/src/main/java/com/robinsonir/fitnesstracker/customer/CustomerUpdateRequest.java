package com.robinsonir.fitnesstracker.customer;

public record CustomerUpdateRequest(
      String name,
      String email,
      Integer age
) {
}
