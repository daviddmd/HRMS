package com.example.jobservice.Services;

import com.example.jobservice.Clients.DepartmentServiceClient;
import com.example.jobservice.Clients.EmployeeServiceClient;
import com.example.jobservice.Entities.Contract;
import com.example.jobservice.Entities.Job;
import com.example.jobservice.Exceptions.CustomException;
import com.example.jobservice.Models.Contract.ContractCreateDTO;
import com.example.jobservice.Models.Contract.ContractQueryDTO;
import com.example.jobservice.Models.Contract.ContractUpdateDTO;
import com.example.jobservice.Models.Department.DepartmentDTO;
import com.example.jobservice.Models.Employee.EmployeeDTO;
import com.example.jobservice.Repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    private final JobService jobService;

    private final EmployeeServiceClient employeeServiceClient;

    private final DepartmentServiceClient departmentServiceClient;

    public Contract getById(Long id) {
        return contractRepository.findById(id).orElseThrow(() -> new CustomException(String.format("No Contract with ID %d", id), HttpStatus.NOT_FOUND));
    }

    public Contract createContract(ContractCreateDTO dto) {
        //Check if the employee exists by its ID
        ResponseEntity<EmployeeDTO> employeeDTO = employeeServiceClient.getEmployee(dto.getEmployeeId());
        if (employeeDTO.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(String.format("No Employee with ID %d", dto.getEmployeeId()), HttpStatus.BAD_REQUEST);
        }
        //Check if the department exists by its ID
        if (dto.getDepartmentId() != null) {
            ResponseEntity<DepartmentDTO> departmentDTO = departmentServiceClient.getDepartment(dto.getDepartmentId());
            if (departmentDTO.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(String.format("No Department with ID %d", dto.getDepartmentId()), HttpStatus.BAD_REQUEST);
            }
        }
        //Check if a contract for the employee exists that has not been terminated before creating the current one
        if (contractRepository.existsByEmployeeIdAndEndingDateNotNull(dto.getEmployeeId())) {
            throw new CustomException(String.format("Employee with ID %d has a contract that has not yet been terminated.", dto.getEmployeeId()), HttpStatus.BAD_REQUEST);
        }

        Job job = jobService.getById(dto.getJobId());
        Contract contract = Contract.builder().
                employeeId(dto.getEmployeeId()).
                startingDate(dto.getStartingDate()).
                job(job).
                departmentId(dto.getDepartmentId()).
                salary(dto.getSalary().setScale(2, RoundingMode.HALF_UP)).
                contractType(dto.getContractType()).build();
        return contractRepository.save(contract);
    }

    public Contract updateContract(Contract contract, ContractUpdateDTO dto) {
        if (dto.getEndingDate() != null && dto.getEndingDate().isBefore(contract.getStartingDate())) {
            throw new CustomException("The contract's ending date cannot be before the contract's beginning date", HttpStatus.BAD_REQUEST);
        }
        if (dto.getDepartmentId() != null) {
            ResponseEntity<DepartmentDTO> departmentDTO = departmentServiceClient.getDepartment(dto.getDepartmentId());
            if (departmentDTO.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(String.format("No Department with ID %d", dto.getDepartmentId()), HttpStatus.BAD_REQUEST);
            }
        }
        contract.setDepartmentId(dto.getDepartmentId());
        contract.setEndingDate(dto.getEndingDate());
        contract.setContractType(dto.getContractType());
        contract.setSalary(dto.getSalary().setScale(2, RoundingMode.HALF_UP));
        return contractRepository.save(contract);
    }

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public List<Contract> getContractsEmployee(Long employeeId) {
        return queryContracts(ContractQueryDTO.builder().employeeId(employeeId).build());
    }

    public List<Contract> queryContracts(ContractQueryDTO dto) {
        Stream<Contract> contractStream = getContracts().stream();
        //Filter by Employee ID
        if (dto.getEmployeeId() != null) {
            contractStream = contractStream.filter(contract -> contract.getEmployeeId().equals(dto.getEmployeeId()));
        }
        //Filter by Job ID
        if (dto.getJobId() != null) {
            contractStream = contractStream.filter(contract -> contract.getJob().getId().equals(dto.getJobId()));
        }
        //Filter by Department ID
        if (dto.getDepartmentId() != null) {
            contractStream = contractStream.filter(contract -> contract.getDepartmentId().equals(dto.getDepartmentId()));
        }
        //Filter by Contract Type ID
        if (dto.getContractType() != null) {
            contractStream = contractStream.filter(contract -> contract.getContractType() == dto.getContractType());
        }
        //Filter by Contract Date
        if (dto.getStartingDateAfter() != null && dto.getEndingDateBefore() != null) {
            if (dto.getStartingDateAfter().isAfter(dto.getEndingDateBefore())) {
                throw new CustomException("Ending Date cannot be before Starting Date", HttpStatus.BAD_REQUEST);
            }
        }
        else if (dto.getStartingDateAfter() != null) {
            contractStream = contractStream.filter(contract -> contract.getStartingDate().isAfter(dto.getStartingDateAfter()));
        }
        else if (dto.getEndingDateBefore() != null) {
            contractStream = contractStream.filter(contract -> contract.getEndingDate() != null && contract.getEndingDate().isBefore(dto.getEndingDateBefore()));
        }
        //Filter by Contract Salary
        if (dto.getSalaryGreaterThan() != null && dto.getSalaryLesserThan() != null) {
            contractStream = contractStream.filter(contract -> contract.getSalary().compareTo(dto.getSalaryGreaterThan()) >= 0 && contract.getSalary().compareTo(dto.getSalaryLesserThan()) <= 0);
        }
        else if (dto.getSalaryGreaterThan() != null) {
            contractStream = contractStream.filter(contract -> contract.getSalary().compareTo(dto.getSalaryGreaterThan()) >= 0);
        }
        else if (dto.getSalaryLesserThan() != null) {
            contractStream = contractStream.filter(contract -> contract.getSalary().compareTo(dto.getSalaryLesserThan()) <= 0);
        }
        return contractStream.toList();
    }

    public void deleteContract(Contract contract) {
        contractRepository.delete(contract);
    }

    public void deleteContractsFromEmployee(Long employeeId) {
        contractRepository.deleteAll(getContractsEmployee(employeeId));
    }
}
