package com.example.departmentservice.Controllers;

import com.example.departmentservice.Models.Location.LocationCreateDTO;
import com.example.departmentservice.Models.Location.LocationDTO;
import com.example.departmentservice.Models.Location.LocationUpdateDTO;
import com.example.departmentservice.Services.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @RolesAllowed({"administration"})
    LocationDTO createLocation(@RequestBody @Valid LocationCreateDTO dto, @AuthenticationPrincipal Jwt principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return mapper.map(locationService.createLocation(dto), LocationDTO.class);
    }

    @GetMapping
    @RolesAllowed({"administration", "human-resources"})
    List<LocationDTO> getLocations() {
        return locationService.getAll().stream().map(location -> mapper.map(location, LocationDTO.class)).toList();
    }

    @GetMapping("/{id}")
    @RolesAllowed({"administration", "human-resources"})
    LocationDTO getLocation(@PathVariable Long id) {
        return mapper.map(locationService.getById(id), LocationDTO.class);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"administration"})
    LocationDTO updateLocation(@PathVariable Long id, @RequestBody @Valid LocationUpdateDTO dto) {
        return mapper.map(locationService.updateLocation(locationService.getById(id), dto), LocationDTO.class);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"administration"})
    void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(locationService.getById(id));
    }
}
