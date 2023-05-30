package com.internproject.shippingservice.service;

import com.internproject.shippingservice.config.JwtUtils;
import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.entity.Rating;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.exception.RatingException;
import com.internproject.shippingservice.mapper.RatingMapstruct;
import com.internproject.shippingservice.repository.IRatingRepository;
import com.internproject.shippingservice.repository.IShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private IRatingRepository ratingRepository;
    private OrderService orderService;
    private JwtUtils jwtUtils;
    private IShipRepository shipRepository;
    private RatingMapstruct ratingMapstruct;
    @Autowired
    public RatingService(IRatingRepository ratingRepository,
                         OrderService orderService,
                         JwtUtils jwtUtils,
                         IShipRepository shipRepository,
                         RatingMapstruct ratingMapstruct) {
        this.ratingRepository = ratingRepository;
        this.orderService = orderService;
        this.jwtUtils = jwtUtils;
        this.shipRepository = shipRepository;
        this.ratingMapstruct = ratingMapstruct;
    }

    public void addRating(RatingDTO ratingDTO, String authorizationToken) {
        String orderId = orderService.getOrderByProductId(ratingDTO.getProductId(), authorizationToken);
        if (orderId == null) {
            throw new RatingException("If you want to rating this product, you must buy this product yet");
        }
        Optional<Ship> shipOptional = shipRepository.findByOrderId(orderId);
        if (!shipOptional.isPresent()) {
            throw new RatingException("Something went wrong!");
        }
        if (!shipOptional.get().getStatus().equals("COMPLETE")) {
            throw new RatingException("This order must complete shipping before rating");
        }
        Rating rating = ratingMapstruct.toRating(ratingDTO);
        rating.setUserId(getIdFromBearerToken(authorizationToken));
        ratingRepository.save(rating);
    }

    public List<RatingDTO> getAll(String id) {
        List<Rating> ratings = ratingRepository.findByProductId(id);
        List<RatingDTO> ratingDto = ratings.stream().map(rating -> ratingMapstruct.toRatingDto(rating)).collect(Collectors.toList());
        return ratingDto;
    }

    private String getIdFromBearerToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }
}
