package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Order {

    private Long id;

    private List<Product> orderedProducts;
    private Integer totalPrice;
    private String state;

    @Builder
    public Order(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
        this.totalPrice = calculateTotalPrice(orderedProducts);
        this.state = "CREATED";
    }

    private Integer calculateTotalPrice(List<Product> orderedProducts) {
        return orderedProducts.stream().mapToInt(
                orderProduct ->
                    orderProduct.getPrice() * orderProduct.getAmount()
        ).sum();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean sameId(Long id){
        return this.id.equals(id);
    }

    public void changeState(String state) {
        this.state = state;
    }
}
