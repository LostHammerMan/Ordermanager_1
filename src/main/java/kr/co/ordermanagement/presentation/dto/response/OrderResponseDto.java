package kr.co.ordermanagement.presentation.dto.response;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.presentation.dto.OrderedProductDto;
import kr.co.ordermanagement.presentation.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@NoArgsConstructor
@Slf4j
public class OrderResponseDto {

    private Long id;
    private List<OrderedProductDto> orderProducts;
    private Integer totalPrice;
    private State state;

    @Builder
    public OrderResponseDto(Long id, List<OrderedProductDto> orderProducts, Integer totalPrice, State state) {
        this.id = id;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    // order -> responseDto
    public static OrderResponseDto toDto(Order order){
        List<OrderedProductDto> orderedProductDtos = order.getOrderedProducts()
                .stream().map(orderedProduct ->
                        OrderedProductDto.toDto(orderedProduct)

                ).toList();

        for(OrderedProductDto dtos : orderedProductDtos){
            log.info("dtos.getAmount() = {}", dtos.getAmount());
            log.info("dtos.getPrice() = {}", dtos.getPrice());
        }

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .id(order.getId())
                .orderProducts(orderedProductDtos)
                .totalPrice(order.getTotalPrice())
                .state(order.getState())
                .build();

        log.info("orderResponseDto.getTotalPrice() = {}", orderResponseDto.getTotalPrice());
        return orderResponseDto;
    }
}
