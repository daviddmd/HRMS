package com.example.departmentservice.Models.Location;

import com.example.departmentservice.Models.Department.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LocationDTO extends LocationDTOSimple {
    private List<DepartmentDTO> departmentList;
}
