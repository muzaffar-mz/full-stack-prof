package com.muzaffar.customer;


/**
 * Created on 01/11/24.
 */

public record CustomerEditRequest(
        String name,
        String email,
        Integer age
) {
}
