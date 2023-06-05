package com.internproject.shippingservice.service;

import com.internproject.shippingservice.config.JwtUtils;
import com.internproject.shippingservice.dto.OrderDTO;
import com.internproject.shippingservice.dto.RatingDTO;
import com.internproject.shippingservice.dto.ShipDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Ship;
import com.internproject.shippingservice.exception.DistrictNotFoundException;
import com.internproject.shippingservice.exception.RatingException;
import com.internproject.shippingservice.exception.ShipException;
import com.internproject.shippingservice.exception.ShipNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class FacadeTest {
    @Mock
    private ShipService shipService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserService userService;
    @Mock
    private DistrictService districtService;
    @Mock
    private OrderService orderService;
    @Mock
    private RatingService ratingService;
    @InjectMocks
    private Facade facade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createShip_ShouldCreateShip() {
        List<ShipDTO> ships = new ArrayList<>();
        ShipDTO ship1 = ShipDTO.builder()
                .orderId("orderId1")
                .status("SHIPPING")
                .build();
        ShipDTO ship2 = ShipDTO.builder()
                .orderId("orderId2")
                .status("SHIPPING")
                .build();
        ships.add(ship1);
        ships.add(ship2);

        facade.createShip(ships);
        verify(shipService).createShip(ships);
    }

    @Test
    public void updateTracking_ShouldUpdate() {
        String id = "shipId";
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        int districtId = 123;
        District district = District.builder().id(districtId).build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(userService.getDistrict(userId, authorizationHeader)).thenReturn(districtId);

        when(districtService.getDistrict(districtId)).thenReturn(district);

        facade.updateTracking(id, authorizationHeader);
        verify(shipService).updateTracking(id, district);
    }

    @Test
    public void updateTracking_ShouldDistrictNotFoundException() {
        String id = "shipId";
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        int districtId = 123;
        District district = District.builder().id(districtId).build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(userService.getDistrict(userId, authorizationHeader)).thenReturn(districtId);

        when(districtService.getDistrict(districtId)).thenThrow(DistrictNotFoundException.class);
        assertThrows(DistrictNotFoundException.class, () -> facade.updateTracking(id, authorizationHeader));
    }

    @Test
    public void trackingOrder_ShouldTrackingOrder() {
        String shipId = "shipId";
        ShipDTO expectedShip = ShipDTO.builder()
                .id(shipId)
                .orderId("orderId")
                .status("SHIPPING")
                .build();

        when(shipService.trackingOrder(shipId)).thenReturn(expectedShip);

        ShipDTO actualShip = facade.trackingOrder(shipId);
        verify(shipService).trackingOrder(shipId);

        assertEquals(expectedShip, actualShip);
    }

    @Test
    public void trackingOrder_ShouldThrowShipNotFoundException() {
        String shipId = "shipId";
        ShipDTO expectedShip = ShipDTO.builder()
                .id(shipId)
                .orderId("orderId")
                .status("SHIPPING")
                .build();

        when(shipService.trackingOrder(shipId)).thenThrow(ShipNotFoundException.class);

        assertThrows(ShipNotFoundException.class, () -> facade.trackingOrder(shipId));
    }

    @Test
    public void completeShipping_ShouldCompleteShipping() {
        String shipId = "shipId";
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        String orderId = "orderId";
        Ship ship = Ship.builder()
                .id(shipId)
                .orderId(orderId)
                .status("SHIPPING")
                .build();
        OrderDTO orderDTO = OrderDTO.builder()
                .id(orderId)
                .shippingFee(0)
                .priceTotal(0)
                .userId(userId)
                .build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(shipService.findShipById(shipId)).thenReturn(ship);
        when(orderService.getOrder(ship.getOrderId(), authorizationHeader)).thenReturn(orderDTO);

        facade.completeShipping(shipId, authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(shipService).updateShipStatus(ship, "COMPLETE");
    }

    @Test
    public void completeShipping_ThrowsShipException() {
        String shipId = "shipId";
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        String orderId = "orderId";
        Ship ship = Ship.builder()
                .id(shipId)
                .orderId(orderId)
                .status("SHIPPING")
                .build();
        OrderDTO orderDTO = OrderDTO.builder()
                .id(orderId)
                .shippingFee(0)
                .priceTotal(0)
                .userId("userId 1")
                .build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(shipService.findShipById(shipId)).thenReturn(ship);
        when(orderService.getOrder(ship.getOrderId(), authorizationHeader)).thenReturn(orderDTO);

        assertThrows(ShipException.class, () -> facade.completeShipping(shipId, authorizationHeader));
    }

    @Test
    public void addRating_ShouldAddRating() {
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        String productId = "productId";
        String orderId = "orderId";
        RatingDTO ratingDTO = RatingDTO.builder()
                .userId(userId)
                .productId(productId)
                .review("review")
                .rating(4)
                .build();
        Ship ship = Ship.builder()
                .orderId(orderId)
                .status("COMPLETE")
                .build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(orderService.getOrderByProductId(productId, authorizationHeader)).thenReturn(orderId);
        when(shipService.findShipByOrderId(orderId)).thenReturn(ship);

        facade.addRating(ratingDTO, authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(ratingService).addRating(ratingDTO, userId);
    }

    @Test
    public void addRating_ShouldThrowRatingException_NotComplete() {
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        String productId = "productId";
        String orderId = "orderId";
        RatingDTO ratingDTO = RatingDTO.builder()
                .userId(userId)
                .productId(productId)
                .review("review")
                .rating(4)
                .build();
        Ship ship = Ship.builder()
                .orderId(orderId)
                .status("SHIPPING")
                .build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(orderService.getOrderByProductId(productId, authorizationHeader)).thenReturn(orderId);
        when(shipService.findShipByOrderId(orderId)).thenReturn(ship);

        assertThrows(RatingException.class, () -> facade.addRating(ratingDTO, authorizationHeader));
    }

    @Test
    public void addRating_ShouldThrowRatingException_NotPurchase() {
        String authorizationHeader = "Bearer <jwtToken>";
        String userId = "userId";
        String productId = "productId";
        String orderId = null;
        RatingDTO ratingDTO = RatingDTO.builder()
                .userId(userId)
                .productId(productId)
                .review("review")
                .rating(4)
                .build();
        Ship ship = Ship.builder()
                .orderId("orderId")
                .status("SHIPPING")
                .build();

        when(jwtUtils.getIdFromJwtToken(anyString())).thenReturn(userId);
        when(orderService.getOrderByProductId(productId, authorizationHeader)).thenReturn(orderId);
        assertThrows(RatingException.class, () -> facade.addRating(ratingDTO, authorizationHeader));
    }

    @Test
    public void getAllRatingByProductId_ShouldReturnList() {
        String productId = "productId";
        List<RatingDTO> expectedRatings = new ArrayList<>();
        when(ratingService.getAll(productId)).thenReturn(expectedRatings);
        List<RatingDTO> actualRatings = facade.getAllRatingByProductId(productId);
        verify(ratingService).getAll(productId);
        assertEquals(expectedRatings, actualRatings);
    }
}
