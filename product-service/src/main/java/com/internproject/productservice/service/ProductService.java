package com.internproject.productservice.service;

import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.exception.ChangeProductDetailException;
import com.internproject.productservice.exception.ProductNotFoundException;
import com.internproject.productservice.mapper.ProductMapstruct;
import com.internproject.productservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService{
    private IProductRepository productRepository;
    private ProductMapstruct productMapstruct;

    @Autowired
    public ProductService(IProductRepository productRepository,
                          ProductMapstruct productMapstruct) {
        this.productRepository = productRepository;
        this.productMapstruct = productMapstruct;
    }

    public void saveProduct(ProductDTO productDTO, String id, Category category) {
        Product product = productMapstruct.toProduct(productDTO);
        product.setSellerId(id);
        product.setCategory(category);
        productRepository.save(product);
    }

    public void saveProductImage(String id, MultipartFile productImage, String userId) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        Product product = productOptional.get();
        if (!userId.equals(product.getSellerId())) {
            throw new ChangeProductDetailException("You can not change detail of this product");
        }
        try {
            byte[] productImageData = productImage.getBytes();
            product.setProductImage(productImageData);
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductDTO getProductById(String id) {
        Optional<Product> productOptional = productRepository.getProduct(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        Product product = productOptional.get();
        ProductDTO productDTO = productMapstruct.toProductDTO(product);
        return productDTO;
    }

    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.getAllProduct();
        List<ProductDTO> productDTOS = products.stream().map(product -> productMapstruct.toProductDTO(product)).collect(Collectors.toList());
        return productDTOS;
    }

    public void updateProduct(String id, ProductDTO productDTO, String userId, Category category) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        if (!productOptional.get().getSellerId().equals(userId)) {
            throw new ChangeProductDetailException("You can not change detail of this product");
        }
        Product product = productMapstruct.toProduct(productDTO);
        product.setCategory(category);
        product.setId(id);
        product.setSellerId(userId);
        product.setProductImage(productOptional.get().getProductImage());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String id, String userId) {
        productRepository.deleteProduct(id, userId);
    }

    public List<ProductDTO> getAllById(List<String> request) {
        List<Product> products = productRepository.findAllById(request);
        List<ProductDTO> productDTOS = products.stream().map(product -> productMapstruct.toProductDTO(product)).collect(Collectors.toList());
        return productDTOS;
    }

    @Transactional
    public void decreaseQuantity(Map<String, Integer> request) {
        for (String key : request.keySet()) {
            productRepository.decreaseQuantity(key, request.get(key));
        }
    }

    public List<ProductDTO> search(String keyWord) {
        List<Product> result = productRepository.findByProductNameContainingIgnoreCase(keyWord);
        if (result.isEmpty()) {
            throw new ProductNotFoundException(String.format("Can not find any product with keyword: %s", keyWord));
        }
        List<ProductDTO> products = result.stream().map(res -> productMapstruct.toProductDTO(res)).collect(Collectors.toList());
        return products;
    }

    public List<ProductDTO> filterProduct(String categoryId, Integer minPrice, Integer maxPrice) {
        List<Product> result = productRepository.filterByCategoryAndPrice(categoryId, minPrice, maxPrice);
        if (result.isEmpty()) {
            throw new ProductNotFoundException("Can not find any product with this filter");
        }
        List<ProductDTO> products = result.stream().map(res -> productMapstruct.toProductDTO(res)).collect(Collectors.toList());
        return products;
    }
}
