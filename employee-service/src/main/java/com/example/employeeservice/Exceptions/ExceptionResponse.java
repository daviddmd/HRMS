package com.example.employeeservice.Exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String message;
}
