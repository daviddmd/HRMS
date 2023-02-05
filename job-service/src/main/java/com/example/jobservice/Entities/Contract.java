package com.example.jobservice.Entities;

import com.example.jobservice.Enums.ContractType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private Long employeeId;

    @NotNull
    private LocalDate startingDate;
    private LocalDate endingDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference(value = "job-contract")
    private Job job;

    @NotNull
    private Long departmentId;

    @NotNull
    @Min(0)
    private BigDecimal salary;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

}
