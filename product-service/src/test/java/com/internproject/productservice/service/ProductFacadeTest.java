package com.internproject.productservice.service;

import com.internproject.productservice.config.JwtUtils;
import com.internproject.productservice.dto.CategoryDTO;
import com.internproject.productservice.dto.ProductDTO;
import com.internproject.productservice.dto.RatingDTO;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.exception.CategoryNotFoundException;
import com.internproject.productservice.exception.ChangeProductDetailException;
import com.internproject.productservice.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductFacadeTest {
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private ProductService productService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private RatingService ratingService;
    @InjectMocks
    private ProductFacade productFacade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveProduct_ShouldSaveProduct() {
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();
        String categoryName = "categoryName";
        category.setCategoryName(categoryName);
        categoryDTO.setCategoryName(categoryName);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("product");
        productDTO.setCategory(categoryDTO);

        when(categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName()))
                .thenReturn(category);

        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        productFacade.saveProduct(productDTO, authorizationHeader);

        verify(categoryService).getCategoryByCategoryName(categoryName);
        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(productService).saveProduct(productDTO, userId, category);
    }

    @Test
    public void saveProduct_ShouldThrowCategoryNotFoundException() {
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();
        String categoryName = "categoryName";
        category.setCategoryName(categoryName);
        categoryDTO.setCategoryName(categoryName);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("product");
        productDTO.setCategory(categoryDTO);
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        when(categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName()))
                .thenThrow(CategoryNotFoundException.class);
        assertThrows(CategoryNotFoundException.class, () -> productFacade.saveProduct(productDTO, authorizationHeader));
    }

    @Test
    public void saveProductImage_ShouldSaveProductImage() throws Exception{
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String productId = "product id";
        InputStream inputStream = getClass().getResourceAsStream("/test-image.jpg"); // Provide the path to a test image
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", inputStream);
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        productFacade.saveProductImage(file, productId, authorizationHeader);
        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(productService).saveProductImage(productId, file, userId);
    }

    @Test
    public void saveProductImage_ShouldThrowProductNotFoundException() throws Exception{
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String productId = "product id";
        InputStream inputStream = getClass().getResourceAsStream("/test-image.jpg"); // Provide the path to a test image
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", inputStream);
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        doThrow(ProductNotFoundException.class).when(productService).saveProductImage(productId, file, userId);
        assertThrows(ProductNotFoundException.class, () -> productFacade.saveProductImage(file, productId, authorizationHeader));
    }

    @Test
    public void saveProductImage_ShouldThrowChangeProductDetailException() throws Exception{
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String productId = "product id";
        InputStream inputStream = getClass().getResourceAsStream("/test-image.jpg"); // Provide the path to a test image
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", inputStream);
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        doThrow(ChangeProductDetailException.class).when(productService).saveProductImage(productId, file, userId);
        assertThrows(ChangeProductDetailException.class, () -> productFacade.saveProductImage(file, productId, authorizationHeader));
    }

    @Test
    public void getProductById_ShouldReturnProductDTO() {
        String productId = "product Id";
        String authorizationHeader = "Token <Bearer>";
        ProductDTO product = new ProductDTO();
        when(productService.getProductById(productId)).thenReturn(product);

        List<RatingDTO> ratings = new ArrayList<>();
        when(ratingService.getRates(product.getId(), authorizationHeader)).thenReturn(ratings);

        ProductDTO productResult = productFacade.getProductById(productId, authorizationHeader);
        verify(productService).getProductById(productId);
        verify(ratingService).getRates(productResult.getId(), authorizationHeader);
        assertEquals(productResult, product);
    }

    @Test
    public void getProductById_ShouldThrowProductNotFoundException() {
        String productId = "product Id";
        String authorizationHeader = "Token <Bearer>";
        ProductDTO product = new ProductDTO();
        when(productService.getProductById(productId)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productFacade.getProductById(productId, authorizationHeader));
    }

    @Test
    public void getAllProduct_ShouldGetAllProduct() {
        List<ProductDTO> products = new ArrayList<>();
        when(productService.getAllProduct()).thenReturn(products);

        List<ProductDTO> productsResult = productFacade.getAllProduct();
        verify(productService).getAllProduct();
        assertEquals(productsResult, products);
    }

    @Test
    public void updateProduct_ShouldUpdateProduct() {
        String productId = "product id";
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String categoryName = "Category Name";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("updateProduct");
        productDTO.setCategory(new CategoryDTO());
        productDTO.getCategory().setCategoryName(categoryName);
        Category category = new Category();
        category.setCategoryName(categoryName);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        when(categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName()))
                .thenReturn(category);

        productFacade.updateProduct(productId, productDTO, authorizationHeader);
        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(categoryService).getCategoryByCategoryName(productDTO.getCategory().getCategoryName());
        verify(productService).updateProduct(productId, productDTO, userId, category);
    }

    @Test
    public void updateProduct_ShouldThrowProductNotFoundException() {
        String productId = "product id";
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String categoryName = "Category Name";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("updateProduct");
        productDTO.setCategory(new CategoryDTO());
        productDTO.getCategory().setCategoryName(categoryName);
        Category category = new Category();
        category.setCategoryName(categoryName);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        when(categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName()))
                .thenReturn(category);

        doThrow(ProductNotFoundException.class).when(productService).updateProduct(productId, productDTO, userId, category);
        assertThrows(ProductNotFoundException.class,
                () -> productFacade.updateProduct(productId, productDTO, authorizationHeader));
    }

    @Test
    public void updateProduct_ShouldThrowChangeProductDetailException() {
        String productId = "product id";
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String categoryName = "Category Name";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("updateProduct");
        productDTO.setCategory(new CategoryDTO());
        productDTO.getCategory().setCategoryName(categoryName);
        Category category = new Category();
        category.setCategoryName(categoryName);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        when(categoryService.getCategoryByCategoryName(productDTO.getCategory().getCategoryName()))
                .thenReturn(category);

        doThrow(ChangeProductDetailException.class).when(productService).updateProduct(productId, productDTO, userId, category);
        assertThrows(ChangeProductDetailException.class,
                () -> productFacade.updateProduct(productId, productDTO, authorizationHeader));
    }

    @Test
    public void deleteProduct_ShouldDeleteProduct() {
        String productId = "product id";
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        productFacade.deleteProduct(productId, authorizationHeader);
        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(productService).deleteProduct(productId, userId);
    }

    @Test
    public void search_ShouldReturnResult() {
        String keyWord = "keyword";
        List<ProductDTO> products = new ArrayList<>();

        when(productService.search(keyWord)).thenReturn(products);

        List<ProductDTO> result = productFacade.search(keyWord);

        verify(productService).search(keyWord);
        assertEquals(result, products);
    }

    @Test
    public void search_ShouldThrowProductNotFoundException() {
        String keyWord = "keyword";
        List<ProductDTO> products = new ArrayList<>();

        when(productService.search(keyWord))
                .thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class, () -> productFacade.search(keyWord));
    }

    @Test
    public void filterProduct_ShouldReturnList() {
        String categoryId = "category id";
        Integer minPrice = 0;
        Integer maxPrice = 999;
        List<ProductDTO> products = new ArrayList<>();

        when(productService.filterProduct(categoryId, minPrice, maxPrice)).thenReturn(products);

        List<ProductDTO> result = productFacade.filterProduct(categoryId, minPrice, maxPrice);

        verify(productService).filterProduct(categoryId, minPrice, maxPrice);
        assertEquals(result, products);
    }

    @Test
    public void filterProduct_ShouldThrowProductNotFoundException() {
        String categoryId = "category id";
        Integer minPrice = 0;
        Integer maxPrice = 999;
        List<ProductDTO> products = new ArrayList<>();

        when(productService.filterProduct(categoryId, minPrice, maxPrice))
                .thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productFacade.filterProduct(categoryId, minPrice, maxPrice));
    }
}
