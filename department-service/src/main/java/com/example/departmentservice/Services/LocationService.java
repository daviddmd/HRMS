package com.example.departmentservice.Services;

import com.example.departmentservice.Entities.Location;
import com.example.departmentservice.Exceptions.CustomException;
import com.example.departmentservice.Models.Location.LocationCreateDTO;
import com.example.departmentservice.Models.Location.LocationUpdateDTO;
import com.example.departmentservice.Repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location getById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new CustomException(String.format("No Location exists with ID %d", id), HttpStatus.NOT_FOUND));
    }

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location createLocation(LocationCreateDTO dto) {
        if (locationRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new CustomException(String.format("A Location with Code %s already exists.", dto.getCode()), HttpStatus.BAD_REQUEST);
        }
        if (!Set.of(Locale.getISOCountries()).contains(dto.getCountryCode())) {
            throw new CustomException(String.format("Invalid Country Code: %s", dto.getCountryCode()), HttpStatus.BAD_REQUEST);
        }
        Location location = Location.builder().
                code(dto.getCode()).
                streetAddress(dto.getStreetAddress()).
                postalCode(dto.getPostalCode()).
                city(dto.getCity()).
                district(dto.getDistrict()).
                countryCode(dto.getCountryCode()).
                departmentList(List.of()).
                build();
        return locationRepository.save(location);
    }

    public Location updateLocation(Location location, LocationUpdateDTO dto) {
        if (!location.getCode().equals(dto.getCode()) && locationRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new CustomException(String.format("A Location with Code %s already exists.", dto.getCode()), HttpStatus.BAD_REQUEST);
        }
        if (!Set.of(Locale.getISOCountries()).contains(dto.getCountryCode())) {
            throw new CustomException(String.format("Invalid Country Code: %s", dto.getCountryCode()), HttpStatus.BAD_REQUEST);
        }
        location.setCode(dto.getCode());
        location.setStreetAddress(dto.getStreetAddress());
        location.setPostalCode(dto.getPostalCode());
        location.setCity(dto.getCity());
        location.setDistrict(dto.getDistrict());
        location.setCountryCode(dto.getCountryCode());
        return locationRepository.save(location);
    }

    public void deleteLocation(Location location) {
        locationRepository.delete(location);
    }
}
