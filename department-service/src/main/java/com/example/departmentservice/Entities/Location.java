package com.example.departmentservice.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    @Column(unique = true)
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
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "location-department")
    @ToString.Exclude
    private List<Department> departmentList;

}
