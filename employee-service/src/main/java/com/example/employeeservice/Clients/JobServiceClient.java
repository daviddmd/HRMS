package com.example.employeeservice.Clients;

import com.example.employeeservice.Models.Contract.ContractDTO;
import com.example.employeeservice.Models.Contract.ContractQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("job-service")
public interface JobServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/contracts/employee/{id}")
    List<ContractDTO> getContracts(@PathVariable Long id);

}
