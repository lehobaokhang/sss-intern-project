package com.internproject.productservice.service;

import com.internproject.productservice.config.JwtUtils;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.dto.RatingDTO;
import com.internproject.productservice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductFacade {
    private JwtUtils jwtUtils;
    private ProductService productService;
    private CategoryService categoryService;
    private RatingService ratingService;

    @Autowired
    public ProductFacade(JwtUtils jwtUtils,
                         ProductService productService,
                         CategoryService categoryService,
                         RatingService ratingService) {
        this.jwtUtils = jwtUtils;
        this.productService = productService;
        this.categoryService = categoryService;
        this.ratingService = ratingService;
    }

    private String getIdFromToken(String authorizationHeader) {
        String id = jwtUtils.getIdFromJwtToken(authorizationHeader);
        return id;
    }

    public void saveProduct(ProductDTO productDTO,
                            String authorizationHeader) {
        Category category = categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName());
        String userId = getIdFromToken(authorizationHeader);
        productService.saveProduct(productDTO, userId, category);
    }

    public void saveProductImage(MultipartFile productImage,
                                String id,
                                String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        productService.saveProductImage(id, productImage, userId);
    }

    public ProductDTO getProductById(String id, String authorizationHeader) {
        ProductDTO product = productService.getProductById(id);
        List<RatingDTO> ratingDTO = ratingService.getRates(product.getId(), authorizationHeader);
        product.setRating(ratingDTO);
        return product;
    }

    public List<ProductDTO> getAllProduct() {
        return productService.getAllProduct();
    }

    public void updateProduct(String id, ProductDTO productDTO, String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        Category category = categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName());
        productService.updateProduct(id, productDTO, userId, category);
    }

    public void deleteProduct(String id, String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        productService.deleteProduct(id, userId);
    }

    public List<ProductDTO> search(String keyWord) {
        return productService.search(keyWord);
    }

    public List<ProductDTO> filterProduct(String categoryId, Integer minPrice, Integer maxPrice) {
        return productService.filterProduct(categoryId, minPrice, maxPrice);
    }

    public List<ProductDTO> getAllById(List<String> request) {
        List<ProductDTO> products = productService.getAllById(request);
        return products;
    }
}
