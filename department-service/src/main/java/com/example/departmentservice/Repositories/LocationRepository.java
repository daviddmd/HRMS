package com.example.departmentservice.Repositories;

import com.example.departmentservice.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByCodeIgnoreCase(String code);

}
