package kr.co.ordermanagement.domain.product;

import kr.co.ordermanagement.domain.exception.LackOfProductAmountException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;


    @Builder
    public Product(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    // 수량 체크
    public void checkEnoughAmount(Integer orderedAmount){
        if (this.amount < orderedAmount){
            throw new LackOfProductAmountException(this);
        }
    }

    // 주문 수량에 따른 수량 감소
    public void decreaseAmount(Integer orderedAmount) {
        this.amount = this.amount - orderedAmount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean sameId(Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }


}
