package com.example.jobservice.Controllers;

import com.example.jobservice.Models.Contract.ContractCreateDTO;
import com.example.jobservice.Models.Contract.ContractDTO;
import com.example.jobservice.Models.Contract.ContractQueryDTO;
import com.example.jobservice.Models.Contract.ContractUpdateDTO;
import com.example.jobservice.Services.ContractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contracts")
@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
@RolesAllowed({"administration", "human-resources", "financial"})
public class ContractController {
    private final ContractService contractService;
    private final ModelMapper mapper;

    @PostMapping
    ContractDTO createContract(@RequestBody @Valid ContractCreateDTO dto) {
        return mapper.map(contractService.createContract(dto), ContractDTO.class);
    }

    @GetMapping
    List<ContractDTO> getContracts(@RequestBody @Valid ContractQueryDTO dto) {
        return contractService.queryContracts(dto).stream().map(contract -> mapper.map(contract, ContractDTO.class)).toList();
    }

    @GetMapping("/employee/{id}")
    List<ContractDTO> getContractsEmployee(@PathVariable Long id) {
        return contractService.getContractsEmployee(id).stream().map(contract -> mapper.map(contract, ContractDTO.class)).toList();
    }

    @GetMapping("/{id}")
    ContractDTO getContract(@PathVariable Long id) {
        return mapper.map(contractService.getById(id), ContractDTO.class);
    }

    @PutMapping("/{id}")
    ContractDTO updateContract(@PathVariable Long id, @RequestBody @Valid ContractUpdateDTO dto) {
        return mapper.map(contractService.updateContract(contractService.getById(id), dto), ContractDTO.class);
    }

    @DeleteMapping("/{id}")
    void deleteContract(@PathVariable Long id) {
        contractService.deleteContract(contractService.getById(id));
    }
}
