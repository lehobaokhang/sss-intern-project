package com.internproject.userservice.fallback;

import com.internproject.userservice.service.ShipService;

public class ShipServiceFallback implements ShipService {
    @Override
    public boolean isDistrictValid(int district, int province, String authorizationHeader) {
        return false;
    }
}