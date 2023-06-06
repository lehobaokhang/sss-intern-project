package com.internproject.shippingservice.service;

import com.internproject.shippingservice.config.JwtUtils;
import com.internproject.shippingservice.dto.OrderDTO;
import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.enumeration.ShipStatusEnum;
import com.internproject.shippingservice.exception.RatingException;
import com.internproject.shippingservice.exception.ShipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Facade {
    private ShipService shipService;
    private JwtUtils jwtUtils;
    private UserService userService;
    private DistrictService districtService;
    private OrderService orderService;
    private RatingService ratingService;

    @Autowired
    public Facade(ShipService shipService,
                  JwtUtils jwtUtils,
                  UserService userService,
                  DistrictService districtService,
                  OrderService orderService,
                  RatingService ratingService) {
        this.shipService = shipService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.districtService = districtService;
        this.orderService = orderService;
        this.ratingService = ratingService;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String id = jwtUtils.getIdFromJwtToken(authorizationHeader);
        return id;
    }

    // Ship Facade
//    public void createShip(List<ShipDTO> ships) {
//        shipService.createShip(ships);
//    }

    public void updateTracking(String id, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        int districtId = userService.getDistrict(userId, authorizationHeader);
        District district = districtService.getDistrict(districtId);
        shipService.updateTracking(id, district);
    }

    public ShipDTO trackingOrder(String id) {
        ShipDTO ship = shipService.trackingOrder(id);
        return ship;
    }

    public void completeShipping(String id, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        Ship ship = shipService.findShipById(id);
        OrderDTO orderDTO = orderService.getOrder(ship.getOrderId(), authorizationHeader);
        if (!orderDTO.getUserId().equals(userId)) {
            throw new ShipException("Can not update status order of another user");
        }
        shipService.updateShipStatus(ship, ShipStatusEnum.COMPLETE.getStatus());
    }

    public boolean isDistrictValid(int district, int province) {
        boolean result = districtService.isDistrictValid(district, province);
        return result;
    }

    // Rating Facade
    public void addRating(RatingDTO ratingDTO, String authorizationHeader) {
        String userId = getIdFromBearerToken(authorizationHeader);
        String orderId = orderService.getOrderByProductId(ratingDTO.getProductId(), authorizationHeader);
        if (orderId == null) {
            throw new RatingException("If you want to rating this product, you must buy this product yet");
        }
        Ship ship = shipService.findShipByOrderId(orderId);
        if (!ship.getStatus().equals(ShipStatusEnum.COMPLETE.getStatus())) {
            throw new RatingException("This order must complete shipping before rating");
        }
        ratingService.addRating(ratingDTO, userId);
    }

    public List<RatingDTO> getAllRatingByProductId(String id) {
        List<RatingDTO> ratings = ratingService.getAll(id);
        return ratings;
    }
}
