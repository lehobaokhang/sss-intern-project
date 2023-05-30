package com.internproject.shippingservice.config;

import com.internproject.shippingservice.dto.DistrictDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Province;
import com.internproject.shippingservice.repository.IProvinceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DistrictProcessor implements ItemProcessor<DistrictDTO, District> {
    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public District process(DistrictDTO district) throws Exception {
        District districtEntity = new District();
        Optional<Province> provinceOptional = provinceRepository.findById(district.getProvinceId());
        districtEntity.setProvince(provinceOptional.get());
        districtEntity.setId(district.getId());
        districtEntity.setDistrictFullName(district.getDistrictFullName());
        districtEntity.setDistrictName(district.getDistrictName());
        return districtEntity;
    }
}
