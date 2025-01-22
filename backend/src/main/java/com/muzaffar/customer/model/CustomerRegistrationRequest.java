package com.muzaffar.customer.model;


/**
 * Created on 31/10/24.
 */

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Integer age,
        Integer gender
) {
}
