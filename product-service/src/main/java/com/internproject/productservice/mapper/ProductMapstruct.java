package com.internproject.productservice.mapper;

import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapstruct {
    Product toProduct(ProductDTO productDTO);
    ProductDTO toProductDTO(Product product);
}
