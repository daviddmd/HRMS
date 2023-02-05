package com.example.employeeservice.Models.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocationDTO {
    private Long id;
    private String code;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String district;
    private String countryCode;
}
