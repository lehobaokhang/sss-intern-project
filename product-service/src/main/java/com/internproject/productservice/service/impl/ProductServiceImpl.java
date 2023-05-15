package com.internproject.productservice.service.impl;

import com.internproject.productservice.dto.CreateProductRequest;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.mapper.ProductMapper;
import com.internproject.productservice.repository.ICategoryRepository;
import com.internproject.productservice.repository.IProductRepository;
import com.internproject.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Product saveProduct(CreateProductRequest request, String id) {
        Product product = ProductMapper.getInstance().toProduct(request);
        product.setSellerId(id);

        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategory());
        if (!categoryOptional.isPresent()) {
            return null;
        }

        product.setCategory(categoryOptional.get());
        productRepository.save(product);
        return product;
    }

    @Override
    public boolean saveProductImage(String id, MultipartFile productImage) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            try {
                byte[] productImageData = productImage.getBytes();
                product.setProductImage(productImageData);
                productRepository.save(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public ProductDTO getProductById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            return null;
        }

        Product product = productOptional.get();

        if (product.isDeleted()) {
            return null;
        }

        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
        return productDTO;
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
