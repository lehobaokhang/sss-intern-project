package com.internproject.productservice.controller;

import com.internproject.productservice.config.JwtUtils;
import com.internproject.productservice.dto.CreateUpdateProductRequest;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/product")
@Api(value = "Product Controller", description = "Product Controller")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    @ApiOperation(value = "Create new product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProduct(@RequestBody CreateUpdateProductRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);

        Product product = productService.saveProduct(request, id);
        return (product != null)
                ? ResponseEntity.ok("Add product successfully")
                : ResponseEntity.badRequest().body("Something went wrong");
    }

    @PostMapping("/add-product-image")
    @ApiOperation(value = "Add or update image for product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> addProductImage(@RequestParam("productImage") MultipartFile productImage, @RequestParam("id") String id) {
        boolean isSuccess = productService.saveProductImage(id, productImage);
        return isSuccess
                ? ResponseEntity.ok("Update image for product is successfully")
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        ProductDTO productResponse = productService.getProductById(id);
        return (productResponse != null)
                ? ResponseEntity.ok(productResponse)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product detail")
    public ResponseEntity<?> updateProduct(@RequestBody CreateUpdateProductRequest request, @PathVariable String id) {
        Product product = productService.updateProduct(id, request);
        return product != null
                ? ResponseEntity.ok("Product has been updated")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete successfully");
    }

}
