package com.example.departmentservice.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String code;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> employees;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference(value = "location-department")
    private Location location;


}
