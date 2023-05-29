package com.internproject.shippingservice.config;

import com.internproject.shippingservice.entity.Province;
import org.springframework.batch.item.ItemProcessor;

public class ProvinceProcessor implements ItemProcessor<Province, Province> {
    @Override
    public Province process(Province province) throws Exception {
        return province;
    }
}
