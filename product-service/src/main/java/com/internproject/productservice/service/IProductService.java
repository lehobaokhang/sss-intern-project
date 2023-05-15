package com.internproject.productservice.service;

import com.internproject.productservice.dto.CreateUpdateProductRequest;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    public Product saveProduct(CreateUpdateProductRequest request, String id);

    public boolean saveProductImage(String id, MultipartFile productImage);

    public ProductDTO getProductById(String id);

    public void deleteProduct(String id);

    public Product updateProduct(String id, CreateUpdateProductRequest createUpdateProductRequest);
}
