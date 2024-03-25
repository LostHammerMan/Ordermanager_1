package kr.co.ordermanagement.domain.order;

import lombok.Builder;
import lombok.Getter;

// 주문된 상품
@Getter
public class OrderedProduct {

    private Long id;
    private String name;
    private Integer Price;
    private Integer amount;

    @Builder
    public OrderedProduct(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        Price = price;
        this.amount = amount;
    }
}
