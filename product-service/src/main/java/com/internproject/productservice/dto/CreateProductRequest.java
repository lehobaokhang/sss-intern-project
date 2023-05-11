package com.internproject.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateProductRequest {
    private String productName;
    private String productSize;
    private int productWeight;
    private String category;
    private Map<String, Set<OptionDetailRequest>> options;
}
