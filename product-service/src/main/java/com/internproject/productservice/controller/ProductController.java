package com.internproject.productservice.controller;

import com.internproject.productservice.config.JwtUtils;
import com.internproject.productservice.dto.CreateProductRequest;
import com.internproject.productservice.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> saveProduct(@RequestBody CreateProductRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);

        productService.saveProduct(request, id);
        return ResponseEntity.ok("Ok");
    }


}
