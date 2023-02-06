package com.example.jobservice.Models.Contract;

import com.example.jobservice.Enums.ContractType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ContractCreateDTO {
    @NotNull
    private Long employeeId;
    @NotNull
    private LocalDate startingDate;

    @NotNull
    private Long jobId;
    private Long departmentId;

    @NotNull
    @Min(0)
    private BigDecimal salary;

    @NotNull
    private ContractType contractType;

}
