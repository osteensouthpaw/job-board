package com.omega.jobportal.company.businessStream;

import com.omega.jobportal.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessStreamService {
    private final BusinessStreamRepository businessStreamRepository;

    public BusinessStream findById(Long id) {
        return businessStreamRepository.findById(id)
                .orElseThrow(() -> new ApiException("business stream does not exist", HttpStatus.NOT_FOUND));
    }
    
    public List<BusinessStream> findAll() {
        return businessStreamRepository.findAll();
    }
}
