package com.internproject.shippingservice.service;

import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.entity.Tracking;
import com.internproject.shippingservice.exception.ShipNotFoundException;
import com.internproject.shippingservice.exception.TrackingException;
import com.internproject.shippingservice.mapper.ShipMapstruct;
import com.internproject.shippingservice.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {
    private IShipRepository shipRepository;
    private ShipMapstruct shipMapstruct;

    @Autowired
    public ShipService(IShipRepository shipRepository,
                       ShipMapstruct shipMapstruct) {
        this.shipRepository = shipRepository;
        this.shipMapstruct = shipMapstruct;
    }

    public void createShip(List<ShipDTO> ships) {
        List<Ship> shipEntity =
                ships.stream().map(ship -> shipMapstruct.toShip(ship)).collect(Collectors.toList());
        try {
            shipRepository.saveAll(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTracking(String id, District district) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(id);
        }
        Ship ship = shipOptional.get();
        Tracking tracking = new Tracking();
        tracking.setDistrict(district);
        ship.addTracking(tracking);
        try {
            shipRepository.save(ship);
        } catch (Exception e) {
            throw new TrackingException("This order has been tracking in this location");
        }
    }

    public ShipDTO trackingOrder(String id) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(id);
        }
        Ship ship = shipOptional.get();
        ShipDTO shipDTO = shipMapstruct.toShipDto(ship);
        for (int i = 0; i < shipDTO.getTracking().size() ; i++) {
            Tracking currentTracking = ship.getTracking().get(i);
            String trackingAddress =
                    String.format("%s, %s", currentTracking.getDistrict().getDistrictFullName(), currentTracking.getDistrict().getProvince().getProvinceFullName());
            shipDTO.getTracking().get(i).setTrackingDistrict(trackingAddress);
        }
        return shipDTO;
    }

    public Ship findShipById(String id) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(id);
        }
        return shipOptional.get();
    }

    public void updateShipStatus(Ship ship, String status) {
        ship.setStatus(status);
        shipRepository.save(ship);
    }

    public Ship findShipByOrderId(String orderId) {
        Optional<Ship> shipOptional = shipRepository.findByOrderId(orderId);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(String.format("Can not find any ship with order's id: %s", orderId));
        }
        return shipOptional.get();
    }
}
