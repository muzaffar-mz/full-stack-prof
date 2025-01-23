package com.muzaffar.auth;


import com.muzaffar.customer.model.CustomerDTO;

/**
 * Created on 23/01/25.
 */

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO) {
}
