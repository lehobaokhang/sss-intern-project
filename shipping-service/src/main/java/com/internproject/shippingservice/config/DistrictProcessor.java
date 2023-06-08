package com.internproject.shippingservice.config;

import com.internproject.shippingservice.dto.DistrictDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Province;
import com.internproject.shippingservice.exception.ProvinceNotFoundException;
import com.internproject.shippingservice.repository.IProvinceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class DistrictProcessor implements ItemProcessor<DistrictDTO, District> {
    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public District process(DistrictDTO district) throws Exception {
        District districtEntity = new District();
        Province province = provinceRepository.findById(district.getProvinceId())
                .orElseThrow(() -> new ProvinceNotFoundException(String.format("Can not find any province with district id: %s", district.getProvinceId())));
        districtEntity.setProvince(province);
        districtEntity.setId(district.getId());
        districtEntity.setDistrictFullName(district.getDistrictFullName());
        districtEntity.setDistrictName(district.getDistrictName());
        return districtEntity;
    }
}
