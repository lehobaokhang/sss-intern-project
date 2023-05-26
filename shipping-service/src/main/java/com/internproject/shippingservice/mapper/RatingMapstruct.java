package com.internproject.shippingservice.mapper;

import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.entity.Rating;
import org.mapstruct.Mapper;

@Mapper
public interface RatingMapstruct {
    Rating toRating(RatingDTO ratingDTO);
    RatingDTO toRatingDto(Rating rating);
}
