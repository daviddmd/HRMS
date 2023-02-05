package com.example.jobservice.Repositories;

import com.example.jobservice.Entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Contract findFirstByEmployeeIdOrderByStartingDateDesc(Long employeeId);

    boolean existsByEmployeeIdAndEndingDateNotNull(Long employeeId);



}
