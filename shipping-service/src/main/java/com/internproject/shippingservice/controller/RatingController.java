package com.internproject.shippingservice.controller;

import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.service.Facade;
import com.internproject.shippingservice.service.RatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@Api(value = "Rating Controller", description = "Rating Controller")
public class RatingController {
    private Facade facade;

    @Autowired
    public RatingController(RatingService ratingService,
                            Facade facade) {
        this.facade = facade;
    }

    @PostMapping
    @ApiOperation(value = "Add new rating for product which customer has been buy and complete")
    public ResponseEntity<String> addRating(@RequestBody RatingDTO ratingDTO,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        facade.addRating(ratingDTO, authorizationHeader);
        return ResponseEntity.ok("Add rating complete");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get all rating of one product")
    public ResponseEntity<List<RatingDTO>> getAll(@PathVariable String id) {
        List<RatingDTO> ratings = facade.getAllRatingByProductId(id);
        return ResponseEntity.ok(ratings);
    }
}
