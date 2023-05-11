package com.internproject.productservice.mapper;


import com.internproject.productservice.dto.CreateProductRequest;
import com.internproject.productservice.entity.OptionDetail;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.entity.ProductOption;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    private static ProductMapper INSTANCE;

    public static ProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public Product toProduct(CreateProductRequest request) {
        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setProductSize(request.getProductSize());
        product.setProductWeight(request.getProductWeight());

        List<ProductOption> productOptions = request.getOptions().entrySet().stream()
                .map(option -> new ProductOption(option.getKey(),
                        option.getValue().stream()
                                .map(detail -> new OptionDetail(detail.getOption_detail_name(), detail.getOption_detail_price(), detail.getOption_detail_quantity()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        product.setOptions(productOptions);

        return product;
    }
}
