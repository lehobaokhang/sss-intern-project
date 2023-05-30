package com.internproject.shippingservice.service;

import com.internproject.shippingservice.dto.CreateShipRequest;
import com.internproject.shippingservice.dto.ShipDTO;
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

    public void createShip(CreateShipRequest ships) {
        List<Ship> shipEntity = ships.getShips().stream().map(ship -> shipMapstruct.toShip(ship)).collect(Collectors.toList());
        try {
            shipRepository.saveAll(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTracking(String id, int districtId) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(String.format("Can not find any shipping information from id: %s", id));
        }
        Ship ship = shipOptional.get();
        Tracking tracking = new Tracking();
        tracking.setLocate(districtId);
        ship.addTracking(tracking);
        try {
            shipRepository.save(ship);
        } catch (Exception e) {
            throw new TrackingException("This order has been tracking in this location");
        }
    }
}
