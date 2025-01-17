package com.omega.jobportal.location;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}
