package com.example.employeeservice.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    @Column(unique = true)
    private String identifier;
    @NotBlank
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @NotNull
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
