package com.internproject.productservice.service;

import com.internproject.productservice.dto.RatingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "SHIP-SERVICE")
public interface RatingService {
    @GetMapping("/rating/{id}")
    List<RatingDTO> getRates(@PathVariable("id") String id);
}