package com.muzaffar.exception;


import java.time.LocalDateTime;

/**
 * Created on 21/01/25.
 */

public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
