package com.example.departmentservice.Exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
}
