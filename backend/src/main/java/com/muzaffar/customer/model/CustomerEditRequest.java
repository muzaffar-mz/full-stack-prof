package com.muzaffar.customer.model;


/**
 * Created on 01/11/24.
 */

public record CustomerEditRequest(
        String name,
        String email,
        Integer age,
        Integer gender
) {
}
