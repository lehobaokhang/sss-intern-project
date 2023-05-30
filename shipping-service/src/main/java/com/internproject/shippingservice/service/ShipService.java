package com.internproject.shippingservice.service;

import com.internproject.shippingservice.dto.CreateShipRequest;
import com.internproject.shippingservice.dto.OrderDTO;
import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.dto.TrackingDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.entity.Tracking;
import com.internproject.shippingservice.exception.ShipException;
import com.internproject.shippingservice.exception.ShipNotFoundException;
import com.internproject.shippingservice.exception.TrackingException;
import com.internproject.shippingservice.mapper.ShipMapstruct;
import com.internproject.shippingservice.repository.IDistrictRepository;
import com.internproject.shippingservice.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {
    private IShipRepository shipRepository;
    private ShipMapstruct shipMapstruct;
    private IDistrictRepository districtRepository;
    private OrderService orderService;

    @Autowired
    public ShipService(IShipRepository shipRepository,
                       ShipMapstruct shipMapstruct,
                       IDistrictRepository districtRepository,
                       OrderService orderService) {
        this.shipRepository = shipRepository;
        this.shipMapstruct = shipMapstruct;
        this.districtRepository = districtRepository;
        this.orderService = orderService;
    }

    public void createShip(CreateShipRequest ships) {
        List<Ship> shipEntity =
                ships.getShips().stream().map(ship -> shipMapstruct.toShip(ship)).collect(Collectors.toList());
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
        District district = districtRepository.findById(districtId).get();
        tracking.setDistrict(district);
        ship.addTracking(tracking);
        try {
            shipRepository.save(ship);
        } catch (Exception e) {
            throw new TrackingException("This order has been tracking in this location");
        }
    }

    public ShipDTO checkShipStatus(String id) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(String.format("Can not find any shipping information from id: %s", id));
        }
        Ship ship = shipOptional.get();
        ShipDTO shipDTO = shipMapstruct.toShipDto(ship);
        for (int i = 0; i < shipDTO.getTracking().size() ; i++) {
            Tracking currentTracking = ship.getTracking().get(i);
            String trackingAddress = String.format("%s, %s", currentTracking.getDistrict().getDistrictFullName(), currentTracking.getDistrict().getProvince().getProvinceFullName());
            shipDTO.getTracking().get(i).setTrackingDistrict(trackingAddress);
        }
        return shipDTO;
    }

    public void completeShip(String id, String userId, String authorizationHeader) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (!shipOptional.isPresent()) {
            throw new ShipNotFoundException(String.format("Can not find any shipping information from id: %s", id));
        }
        Ship ship = shipOptional.get();
        OrderDTO orderDTO = orderService.getOrder(ship.getOrderId(), authorizationHeader);
        if (!orderDTO.getUserId().equals(userId)) {
            throw new ShipException("Can not update status order of another user");
        }
        ship.setStatus("COMPLETE");
        shipRepository.save(ship);
    }
}
