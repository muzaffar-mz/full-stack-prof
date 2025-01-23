package com.muzaffar.auth;


/**
 * Created on 23/01/25.
 */

public record AuthenticationRequest(
        String username,
        String password
) {
}
