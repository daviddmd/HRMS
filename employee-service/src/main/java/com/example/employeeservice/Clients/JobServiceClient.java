package com.example.employeeservice.Clients;

import com.example.employeeservice.Models.Contract.ContractDTO;
import com.example.employeeservice.Models.Contract.ContractQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("job-service")
public interface JobServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/contracts", consumes = "application/json")
    List<ContractDTO> getContracts(ContractQueryDTO dto);

}
