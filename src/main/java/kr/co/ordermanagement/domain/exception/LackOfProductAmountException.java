package kr.co.ordermanagement.domain.exception;

import kr.co.ordermanagement.domain.product.Product;

public class LackOfProductAmountException extends RuntimeException {

    static final String MSG = "번의 상품이 부족합니다";

    public LackOfProductAmountException(Product product) {
        super(product.getId() + MSG);
    }
}
