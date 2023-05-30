package com.internproject.orderservice.mapper;

import com.internproject.orderservice.dto.OrderDTO;
import com.internproject.orderservice.dto.OrderProductDTO;
import com.internproject.orderservice.entity.Order;
import com.internproject.orderservice.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapstruct {
    @Mapping(source = "orderProducts", target = "orderProductsDTO")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "orderProductsDTO", target = "orderProducts")
    Order toOrder(OrderDTO orderDTO);

    OrderProductDTO toOrderProductDTO(OrderProduct orderProduct);
    OrderProduct toOrderProduct(OrderProductDTO orderProductDTO);
}
