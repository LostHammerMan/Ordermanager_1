package kr.co.ordermanagement.domain.exception;

public class OrderStateNotFoundException extends RuntimeException{

    private static final String MSG = "존재하지 않는 주문 상태입니다";

    public OrderStateNotFoundException(){
        super(MSG);
    }
}
