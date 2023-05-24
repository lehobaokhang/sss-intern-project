package com.internproject.productservice.service;

import com.internproject.productservice.dto.request.CreateAndUpdateProductRequest;
import com.internproject.productservice.dto.request.GetProductsByIdsRequest;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    public Product saveProduct(CreateAndUpdateProductRequest request, String id);

    public boolean saveProductImage(String id, MultipartFile productImage, String userId);

    public ProductDTO getProductById(String id);

    public void deleteProduct(String id, String userId);

    public Product updateProduct(String id, CreateAndUpdateProductRequest createUpdateProductRequest, String userId);

    public List<ProductDTO> getAllById(GetProductsByIdsRequest request);
}
