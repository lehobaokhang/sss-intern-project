package com.internproject.productservice.controller;

import com.internproject.productservice.config.JwtUtils;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.dto.request.GetProductsByIdsRequest;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/product")
@Api(value = "Product Controller", description = "Product Controller")
public class ProductController {
    private ProductService productService;
    private JwtUtils jwtUtils;

    @Autowired
    public ProductController(ProductService productService, JwtUtils jwtUtils) {
        this.productService = productService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDTO productDTO,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        productService.saveProduct(productDTO, id);
        return ResponseEntity.ok("Add product successfully");
    }

    @PostMapping("/add-product-image")
    @ApiOperation(value = "Add or update image for product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> addProductImage(@RequestParam("productImage") MultipartFile productImage,
                                                  @RequestParam("id") String id,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        productService.saveProductImage(id, productImage, userId);
        return ResponseEntity.ok("Update image for product is successfully");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        ProductDTO productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    @ApiOperation(value = "Get all product")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product detail")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable String id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        productService.updateProduct(id, productDTO, userId);
        return ResponseEntity.ok("Product has been updated");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        productService.deleteProduct(id, userId);
        return ResponseEntity.ok("Delete successfully");
    }

    @PostMapping("/get-all-by-id")
    @ApiOperation(value = "Get all product which have id in a List<String> in @RequestBody")
    public ResponseEntity<List<ProductDTO>> getAllByID(@RequestBody GetProductsByIdsRequest request) {
        return ResponseEntity.ok(productService.getAllById(request));
    }

    @PostMapping("/decrease-quantity")
    public ResponseEntity<String> decreaseQuantity(@RequestBody Map<String, Integer> request) {
        productService.decreaseQuantity(request);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search product by name")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam("search") String keyWord) {
        List<ProductDTO> products = productService.search(keyWord);
        return ResponseEntity.ok(products);
    }

    private String getIdFromToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }
}
