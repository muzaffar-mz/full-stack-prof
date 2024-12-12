package com.muzaffar.customer;


/**
 * Created on 31/10/24.
 */

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
