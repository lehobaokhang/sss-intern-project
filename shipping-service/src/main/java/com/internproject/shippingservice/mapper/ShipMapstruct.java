package com.internproject.shippingservice.mapper;

import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.dto.TrackingDTO;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.entity.Tracking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ShipMapstruct {
    Ship toShip(ShipDTO shipDTO);
    ShipDTO toShipDto(Ship ship);
    Tracking toTracking(TrackingDTO trackingDTO);
    TrackingDTO toTrackingDto(Tracking tracking);
}
