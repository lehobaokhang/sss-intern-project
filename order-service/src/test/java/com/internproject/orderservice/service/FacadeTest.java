package com.internproject.orderservice.service;

import com.internproject.orderservice.config.JwtUtils;
import com.internproject.orderservice.dto.*;
import com.internproject.orderservice.entity.Cart;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private CartService cartService;
    @Mock
    private ProductService productService;
    @Mock
    private ShipService shipService;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private Facade facade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addCart_ShouldAddToCart() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(1);
        cartDTO.setPrice(2);
        cartDTO.setProductId("product id");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setSellerId("seller id");
        when(productService.getProduct(cartDTO.getProductId())).thenReturn(productDTO);

        Cart cart = new Cart();
        when(cartService.addCart(cartDTO, userId, productDTO)).thenReturn(cart);

        Cart result = facade.addCart(cartDTO, authorizationHeader);
        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(productService).getProduct(cartDTO.getProductId());
        verify(cartService).addCart(cartDTO, userId, productDTO);
        assertEquals(cart, result);
    }

    @Test
    public void addCart_ShouldThrowProductNotFoundException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(1);
        cartDTO.setPrice(2);
        cartDTO.setProductId("product id");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setSellerId("seller id");
        when(productService.getProduct(cartDTO.getProductId()))
                .thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> facade.addCart(cartDTO, authorizationHeader));
    }

    @Test
    public void addCart_ShouldThrowCartException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "seller id";
        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(1);
        cartDTO.setPrice(2);
        cartDTO.setProductId("product id");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setSellerId("seller id");
        when(productService.getProduct(cartDTO.getProductId()))
                .thenThrow(CartException.class);

        assertThrows(CartException.class,
                () -> facade.addCart(cartDTO, authorizationHeader));
    }

    @Test
    public void getAllCart_ShouldReturnList() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user123";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<CartDTO> expectedCarts = new ArrayList<>();
        CartDTO cart1 = new CartDTO();
        cart1.setProductId("product123");
        cart1.setProductName("Product 1");
        CartDTO cart2 = new CartDTO();
        cart2.setProductId("product456");
        cart2.setProductName("Product 2");
        expectedCarts.add(cart1);
        expectedCarts.add(cart2);

        when(cartService.getAll(userId)).thenReturn(expectedCarts);

        when(productService.getProductByIds(List.of("product123", "product456"))).thenReturn(
                List.of(
                        ProductDTO.builder()
                                .id("product123")
                                .productName("Product 1")
                                .build(),
                        ProductDTO.builder()
                                .id("product456")
                                .productName("Product 2")
                                .build()
                )
        );

        List<CartDTO> actualCarts = facade.getAllCart(authorizationHeader);

        assertEquals(expectedCarts.size(), actualCarts.size());
        assertEquals(expectedCarts.get(0).getProductId(), actualCarts.get(0).getProductId());
        assertEquals(expectedCarts.get(0).getProductName(), actualCarts.get(0).getProductName());
        assertEquals(expectedCarts.get(1).getProductId(), actualCarts.get(1).getProductId());
        assertEquals(expectedCarts.get(1).getProductName(), actualCarts.get(1).getProductName());
    }

    @Test
    public void deleteCart_ShouldDeleteCart() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String cartId = "cart id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        facade.deleteCart(cartId, authorizationHeader);

        verify(cartService).deleteCart(cartId, userId);
    }

    @Test
    public void updateCart_ShouldUpdateCart() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String cartId = "cart id";

        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(5);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        facade.updateCart(cartId, cartDTO, authorizationHeader);

        verify(cartService).updateCart(cartId, cartDTO, userId);
    }

    @Test
    public void updateCart_ShouldThrowCartNotFoundException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String cartId = "cart id";

        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(5);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        doThrow(CartNotFoundException.class).when(cartService).updateCart(cartId, cartDTO, userId);

        assertThrows(CartNotFoundException.class,
                () -> facade.updateCart(cartId, cartDTO, authorizationHeader));
    }

    @Test
    public void updateCart_ShouldThrowCartException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String cartId = "cart id";

        CartDTO cartDTO = new CartDTO();
        cartDTO.setQuantity(5);

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        doThrow(CartException.class).when(cartService).updateCart(cartId, cartDTO, userId);

        assertThrows(CartException.class,
                () -> facade.updateCart(cartId, cartDTO, authorizationHeader));
    }

    @Test
    public void addOrder_ShouldAddOrder() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        List<String> cartIds = List.of("cart123", "cart456");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<Cart> carts = new ArrayList<>();
        Cart cart1 = new Cart();
        cart1.setProductId("product123");
        cart1.setQuantity(2);
        cart1.setUserId(userId);
        Cart cart2 = new Cart();
        cart2.setProductId("product456");
        cart2.setQuantity(1);
        cart2.setUserId(userId);
        carts.add(cart1);
        carts.add(cart2);
        when(cartService.getByIds(cartIds)).thenReturn(carts);

        // Mock the behavior of ProductService
        List<String> productIds = List.of("product123", "product456");
        List<ProductDTO> products = new ArrayList<>();
        ProductDTO product1 = new ProductDTO();
        product1.setId("product123");
        product1.setProductName("Product 1");
        ProductDTO product2 = new ProductDTO();
        product2.setId("product456");
        product2.setProductName("Product 2");
        products.add(product1);
        products.add(product2);
        when(productService.getProductByIds(productIds)).thenReturn(products);

        // Mock the behavior of OrderService
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId("order123");
        Order order2 = new Order();
        order2.setId("order456");
        orders.add(order1);
        orders.add(order2);
        when(orderService.saveOrder(carts, products)).thenReturn(orders);

        // Call the method to be tested
        facade.addOrder(cartIds, authorizationHeader);

        // Verify that the necessary methods were called with the correct arguments
        verify(cartService).deleteAllByIds(cartIds);
        verify(productService).decreaseQuantity(Map.of("product123", 2, "product456", 1));
        verify(shipService).createShip(List.of(
                ShipDTO.builder().orderId("order123").status("SHIPPING").build(),
                ShipDTO.builder().orderId("order456").status("SHIPPING").build()
        ));
    }

    @Test
    public void addOrder_ShouldThrowOrderException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        List<String> cartIds = List.of("cart123", "cart456");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<Cart> carts = new ArrayList<>();
        when(cartService.getByIds(cartIds)).thenReturn(carts);

        assertThrows(OrderException.class, () -> facade.addOrder(cartIds, authorizationHeader));
    }

    @Test
    public void addOrder_ShouldThrowOrderExceptionMore() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        List<String> cartIds = List.of("cart123", "cart456");

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<Cart> carts = new ArrayList<>();
        Cart cart1 = new Cart();
        cart1.setProductId("product123");
        cart1.setQuantity(2);
        cart1.setUserId(userId);
        Cart cart2 = new Cart();
        cart2.setProductId("product456");
        cart2.setQuantity(1);
        cart2.setUserId("other user id");
        carts.add(cart1);
        carts.add(cart2);
        when(cartService.getByIds(cartIds)).thenReturn(carts);
        assertThrows(OrderException.class, () -> facade.addOrder(cartIds, authorizationHeader));
    }

    @Test
    public void getAllOrder_ShouldReturnList() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<OrderDTO> orders = new ArrayList<>();
        OrderDTO order1 = new OrderDTO();
        order1.setId("order123");
        OrderDTO order2 = new OrderDTO();
        order2.setId("order456");
        orders.add(order1);
        orders.add(order2);
        when(orderService.getAll(userId)).thenReturn(orders);

        List<OrderDTO> result = facade.getAllOrder(authorizationHeader);

        verify(orderService).getAll(userId);
        assertEquals(result, orders);
    }

    @Test
    public void getOrderById_ShouldReturnOrder() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String orderId = "order id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        OrderDTO order = new OrderDTO();
        order.setId(orderId);
        when(orderService.getOrderById(orderId, userId)).thenReturn(order);

        OrderDTO result = facade.getOrderById(orderId, authorizationHeader);

        verify(orderService).getOrderById(orderId, userId);
        assertEquals(result, order);
    }

    @Test
    public void getOrderById_ShouldThrowOrderNotFoundException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String orderId = "order id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        OrderDTO order = new OrderDTO();
        order.setId("other order id");
        when(orderService.getOrderById(orderId, userId)).thenThrow(OrderNotFoundException.class);
        assertThrows(OrderNotFoundException.class, () -> facade.getOrderById(orderId, authorizationHeader));
    }

    @Test
    public void getOrderByProductID_ShouldReturnOrder() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String productId = "product id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<OrderProductDTO> orderProducts = new ArrayList<>();
        OrderProductDTO orderProduct = new OrderProductDTO();
        orderProduct.setProductId(productId);
        orderProducts.add(orderProduct);

        OrderDTO order = new OrderDTO();
        order.setOrderProductsDTO(orderProducts);

        when(orderService.getOrderById(productId, userId)).thenReturn(order);

        OrderDTO result = facade.getOrderById(productId, authorizationHeader);

        verify(orderService).getOrderById(productId, userId);
        assertEquals(result, order);
    }

    @Test
    public void getOrderByProductID_ShouldThrowOrderNotFoundException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        String productId = "product id";

        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        List<OrderProductDTO> orderProducts = new ArrayList<>();
        OrderProductDTO orderProduct = new OrderProductDTO();
        orderProduct.setProductId(productId);
        orderProducts.add(orderProduct);

        OrderDTO order = new OrderDTO();
        order.setOrderProductsDTO(orderProducts);

        when(orderService.getOrderById(productId, userId)).thenThrow(OrderNotFoundException.class);
        assertThrows(OrderNotFoundException.class, () -> facade.getOrderById(productId, authorizationHeader));
    }
}