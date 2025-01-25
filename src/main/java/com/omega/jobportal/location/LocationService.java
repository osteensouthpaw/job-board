package com.omega.jobportal.location;


import com.omega.jobportal.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Integer id, Location updatedLocation) {
        return locationRepository.findById(id).map(location -> {
            location.setCountryId(updatedLocation.getCountryId());
            location.setCity(updatedLocation.getCity());
            location.setAddressLine1(updatedLocation.getAddressLine1());
            location.setAddressLine2(updatedLocation.getAddressLine2());
            location.setPostalCode(updatedLocation.getPostalCode());
            return locationRepository.save(location);
        }).orElseThrow(() -> new ApiException("location not found", HttpStatus.NOT_FOUND));
    }

    public Location findLocationById(Integer id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ApiException("location does not exists", HttpStatus.NOT_FOUND));
    }
}
