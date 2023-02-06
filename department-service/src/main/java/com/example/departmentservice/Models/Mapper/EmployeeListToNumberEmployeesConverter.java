package com.example.departmentservice.Models.Mapper;

import org.modelmapper.AbstractConverter;

import java.util.List;

public class EmployeeListToNumberEmployeesConverter extends AbstractConverter<List<Long>, Integer> {
    @Override
    protected Integer convert(List<Long> source) {
        return source == null ? 0 : source.size();
    }
}
