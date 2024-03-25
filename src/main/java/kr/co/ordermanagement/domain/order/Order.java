package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.exception.CancellableStateException;
import kr.co.ordermanagement.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Order {

    private Long id;

//    private List<Product> orderedProducts;
    private List<OrderedProduct> orderedProducts;
    private Integer totalPrice;
    private State state;

    @Builder
    public Order(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
        this.totalPrice = calculateTotalPrice(orderedProducts);
        this.state = State.CREATED;
    }

    private Integer calculateTotalPrice(List<OrderedProduct> orderedProducts) {
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

    public void changeState(State state) {
        this.state = state;
    }

    public boolean sameState(State state) {
        return this.state.equals(state);
    }

    public void cancel() {

//        if (!this.state.equals(State.CREATED)){
//            throw new CancellableStateException();
//        }
        this.state.checkCancellable();
        this.state = State.CANCELED;
    }
}
