package com.example.departmentservice.Models.Location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationUpdateDTO {
    @NotBlank
    @NotNull
    private String code;

    @NotBlank
    @NotNull
    private String streetAddress;

    @NotBlank
    @NotNull
    private String postalCode;

    @NotBlank
    @NotNull
    private String city;

    @NotBlank
    @NotNull
    private String district;

    @NotNull
    private String countryCode;
}
