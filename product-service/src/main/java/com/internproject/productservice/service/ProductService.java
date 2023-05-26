package com.internproject.productservice.service;

import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.dto.request.GetProductsByIdsRequest;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.exception.CategoryNotFoundException;
import com.internproject.productservice.exception.ChangeProductDetailException;
import com.internproject.productservice.exception.ProductNotFoundException;
import com.internproject.productservice.mapper.ProductMapstruct;
import com.internproject.productservice.repository.ICategoryRepository;
import com.internproject.productservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService{
    private IUserService userService;
    private IProductRepository productRepository;
    private ICategoryRepository categoryRepository;
    private ProductMapstruct productMapstruct;

    @Autowired
    public ProductService(IUserService userService,
                          IProductRepository productRepository,
                          ICategoryRepository categoryRepository,
                          ProductMapstruct productMapstruct) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapstruct = productMapstruct;
    }

    public void saveProduct(ProductDTO productDTO, String id) {
        Product product = productMapstruct.toProduct(productDTO);
        product.setSellerId(id);

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new CategoryNotFoundException("Can not found any category with id: " + product.getCategory().getId());
        }
        product.setCategory(categoryOptional.get());
        productRepository.save(product);
    }

    public void saveProductImage(String id, MultipartFile productImage, String userId) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
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
        } else {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
    }

    public ProductDTO getProductById(String id) {
        Optional<Product> productOptional = productRepository.getProduct(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        Product product = productOptional.get();
        if (product.isDeleted()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        String sellerFullName = userService.getUserFullName(product.getSellerId());
        ProductDTO productDTO = productMapstruct.toProductDTO(product);
//        productDTO.setSellerFullName(sellerFullName);
        return productDTO;
    }

    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.getAllProduct();
        List<ProductDTO> productDTOS = products.stream().map(product -> productMapstruct.toProductDTO(product)).collect(Collectors.toList());
        return productDTOS;
    }

    public void updateProduct(String id, ProductDTO productDTO, String userId) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(String.format("Can not find any product with id: %s", id));
        }
        if (!productOptional.get().getSellerId().equals(userId)) {
            throw new ChangeProductDetailException("You can not change detail of this product");
        }
        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new CategoryNotFoundException("Can not found any category with id: " + productDTO.getCategory().getId());
        }
        Product product = productMapstruct.toProduct(productDTO);
        product.setId(id);
        product.setSellerId(userId);
        product.setProductImage(productOptional.get().getProductImage());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String id, String userId) {
        productRepository.deleteById(id, userId);
    }

    public List<ProductDTO> getAllById(GetProductsByIdsRequest request) {
        List<Product> products = productRepository.findAllById(request.getId());
        List<ProductDTO> productDTOS = products.stream().map(product -> productMapstruct.toProductDTO(product)).collect(Collectors.toList());
        return productDTOS;
    }
}