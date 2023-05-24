//package com.internproject.productservice.service.impl;
//
//import com.internproject.productservice.dto.request.CreateAndUpdateProductRequest;
//import com.internproject.productservice.dto.request.GetProductsByIdsRequest;
//import com.internproject.productservice.dto.ProductDTO;
//import com.internproject.productservice.entity.Category;
//import com.internproject.productservice.entity.Product;
//import com.internproject.productservice.repository.ICategoryRepository;
//import com.internproject.productservice.repository.IProductRepository;
//import com.internproject.productservice.service.IProductService;
//import com.internproject.productservice.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductServiceImpl implements IProductService {
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private IProductRepository productRepository;
//
//    @Autowired
//    private ICategoryRepository categoryRepository;
//
//    @Override
//    public Product saveProduct(CreateAndUpdateProductRequest request, String id) {
//        Product product = ProductMapper.getInstance().toProduct(request);
//        product.setSellerId(id);
//
//        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategory());
//        if (!categoryOptional.isPresent()) {
//            return null;
//        }
//
//        product.setCategory(categoryOptional.get());
//        productRepository.save(product);
//        return product;
//    }
//
//    @Override
//    public boolean saveProductImage(String id, MultipartFile productImage, String userId) {
//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//            if (!userId.equals(product.getSellerId())) {
//                return false;
//            }
//
//            try {
//                byte[] productImageData = productImage.getBytes();
//                product.setProductImage(productImageData);
//                productRepository.save(product);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public ProductDTO getProductById(String id) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        if (!productOptional.isPresent()) {
//            return null;
//        }
//        Product product = productOptional.get();
//        if (product.isDeleted()) {
//            return null;
//        }
//        String sellerFullName = userService.getUserFullName(product.getSellerId());
//        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
//        productDTO.setSellerFullName(sellerFullName);
//        return productDTO;
//    }
//
//    @Override
//    @Transactional
//    public void deleteProduct(String id, String userId) {
//        productRepository.deleteById(id, userId);
//    }
//
//    @Override
//    public Product updateProduct(String id, CreateAndUpdateProductRequest createUpdateProductRequest, String userId) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        if (!productOptional.isPresent()) {
//            return null;
//        }
//
//        if (!productOptional.get().getSellerId().equals(userId)) {
//            return null;
//        }
//
//        Product product = ProductMapper.getInstance().toProduct(createUpdateProductRequest);
//        product.setId(id);
//
//        Optional<Category> categoryOptional = categoryRepository.findById(createUpdateProductRequest.getCategory());
//        if (!categoryOptional.isPresent()) {
//            return null;
//        }
//
//        product.setCategory(categoryOptional.get());
//        product.setSellerId(productOptional.get().getSellerId());
//        product.setProductImage(productOptional.get().getProductImage());
//        product.setDeleted(productOptional.get().isDeleted());
//        productRepository.save(product);
//
//        return product;
//    }
//
//    @Override
//    public List<ProductDTO> getAllById(GetProductsByIdsRequest request) {
//        List<Product> products = productRepository.findAllById(request.getId());
//        List<ProductDTO> productDTOS = products.stream().map(product -> ProductMapper.getInstance().toDTO(product)).collect(Collectors.toList());
//        return productDTOS;
//    }
//}
