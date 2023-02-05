package com.example.departmentservice.Controllers;

import com.example.departmentservice.Models.Location.LocationCreateDTO;
import com.example.departmentservice.Models.Location.LocationDTO;
import com.example.departmentservice.Models.Location.LocationUpdateDTO;
import com.example.departmentservice.Services.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Locations")
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final ModelMapper mapper;

    @PostMapping
    LocationDTO createLocation(@RequestBody @Valid LocationCreateDTO dto) {
        return mapper.map(locationService.createLocation(dto), LocationDTO.class);
    }

    @GetMapping
    List<LocationDTO> getLocations() {
        return locationService.getAll().stream().map(location -> mapper.map(location, LocationDTO.class)).toList();
    }

    @GetMapping("/{id}")
    LocationDTO getLocation(@PathVariable Long id) {
        return mapper.map(locationService.getById(id), LocationDTO.class);
    }

    @PutMapping("/{id}")
    LocationDTO updateLocation(@PathVariable Long id, @RequestBody @Valid LocationUpdateDTO dto) {
        return mapper.map(locationService.updateLocation(locationService.getById(id), dto), LocationDTO.class);
    }

    @DeleteMapping("/{id}")
    void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(locationService.getById(id));
    }
}
