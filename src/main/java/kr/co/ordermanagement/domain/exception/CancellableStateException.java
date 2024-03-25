package kr.co.ordermanagement.domain.exception;

public class CancellableStateException extends RuntimeException{

    private static final String MSG = "이미 취소되었거나 취소할 수 없는 주문 상태입니다";

    public CancellableStateException() {
        super(MSG);
    }
}
