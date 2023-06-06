package com.internproject.shippingservice.service;

import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.entity.Rating;
import com.internproject.shippingservice.exception.RatingException;
import com.internproject.shippingservice.mapper.RatingMapstruct;
import com.internproject.shippingservice.repository.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private IRatingRepository ratingRepository;
    private RatingMapstruct ratingMapstruct;
    @Autowired
    public RatingService(IRatingRepository ratingRepository,
                         RatingMapstruct ratingMapstruct) {
        this.ratingRepository = ratingRepository;
        this.ratingMapstruct = ratingMapstruct;
    }

    public void addRating(RatingDTO ratingDTO, String userId) {
        Rating rating = ratingMapstruct.toRating(ratingDTO);
        rating.setUserId(userId);
        try {
            ratingRepository.save(rating);
        } catch (DataIntegrityViolationException e) {
            throw new RatingException("You have already rate this product");
        }
    }

    public List<RatingDTO> getAll(String id) {
        List<Rating> ratings = ratingRepository.findByProductId(id);
        List<RatingDTO> ratingDto = ratings.stream().map(rating -> ratingMapstruct.toRatingDto(rating)).collect(Collectors.toList());
        return ratingDto;
    }
}
