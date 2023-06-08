package com.internproject.shippingservice.service;

import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.exception.DistrictNotFoundException;
import com.internproject.shippingservice.repository.IDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistrictService {
    private IDistrictRepository districtRepository;

    @Autowired
    public DistrictService(IDistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District getDistrict(int id) {
        Optional<District> districtOptional = districtRepository.findById(id);
        if (!districtOptional.isPresent()) {
            throw new DistrictNotFoundException(String.format("Can not find any district with id: %d", id));
        }
        return districtOptional.get();
    }

    public boolean isDistrictValid(int district, int province) {
        Optional<District> districtOptional = districtRepository.findById(district);
        return (districtOptional.isPresent() && districtOptional.get().getProvince().getId() == province);
    }
}
