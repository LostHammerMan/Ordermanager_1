package kr.co.ordermanagement.presentation.dto.response;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.product.Product;
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
    private List<ProductDto> orderProducts;
    private Integer totalPrice;
    private String state;

    @Builder
    public OrderResponseDto(Long id, List<ProductDto> orderProducts, Integer totalPrice, String state) {
        this.id = id;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    // order -> responseDto
    public static OrderResponseDto toDto(Order order){
        List<ProductDto> orderedProductDtos = order.getOrderedProducts()
                .stream().map(orderedProduct ->
                        ProductDto.toDto(orderedProduct)

                ).toList();

        for(ProductDto dtos : orderedProductDtos){
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
