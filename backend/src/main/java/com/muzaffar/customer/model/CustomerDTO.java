package com.muzaffar.customer.model;


import java.util.List;

/**
 * Created on 21/01/25.
 */

public record CustomerDTO(
        Long id,
        String name,
        String email,
        Integer gender,
        Integer age,
        List<String> roles,
        String username

) {
}
