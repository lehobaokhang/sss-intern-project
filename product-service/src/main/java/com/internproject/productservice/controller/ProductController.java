package com.internproject.productservice.controller;

import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.service.ProductFacade;
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
    private ProductFacade productFacade;

    @Autowired
    public ProductController(ProductService productService,
                             ProductFacade productFacade) {
        this.productService = productService;
        this.productFacade = productFacade;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDTO productDTO,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        productFacade.saveProduct(productDTO, authorizationHeader);
        return ResponseEntity.ok("Add product successfully");
    }

    @PostMapping("/add-product-image")
    @ApiOperation(value = "Add or update image for product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> addProductImage(@RequestParam("productImage") MultipartFile productImage,
                                                  @RequestParam("id") String id,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        productFacade.saveProductImage(productImage, id, authorizationHeader);
        return ResponseEntity.ok("Update image for product is successfully");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        ProductDTO product = productFacade.getProductById(id, authorizationHeader);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all product")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productFacade.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product detail")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO,
                                                @PathVariable String id,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        productFacade.updateProduct(id, productDTO, authorizationHeader);
        return ResponseEntity.ok("Product has been updated");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        productFacade.deleteProduct(id, authorizationHeader);
        return ResponseEntity.ok("Delete successfully");
    }

    @PostMapping("/get-all-by-id")
    @ApiOperation(value = "Get all product which have id in a List<String> in @RequestBody")
    public ResponseEntity<List<ProductDTO>> getAllByID(@RequestBody List<String> request) {
        return ResponseEntity.ok(productFacade.getAllById(request));
    }

    @PostMapping("/decrease-quantity")
    public ResponseEntity<String> decreaseQuantity(@RequestBody Map<String, Integer> request) {
        productService.decreaseQuantity(request);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search product by name")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam("keyword") String keyWord) {
        List<ProductDTO> products = productFacade.search(keyWord);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Filter product")
    public ResponseEntity<List<ProductDTO>> filterProduct(@RequestParam(value = "category", required = false) String category,
                                                          @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                          @RequestParam(value = "maxPrice", required = false) Integer maxPrice) {
        List<ProductDTO> products = productFacade.filterProduct(category, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}
