package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.exception.CancellableStateException;

public enum State {

    CREATED{
        @Override
        void checkCancellable() {
        }
    }, SHIPPING, COMPLETED, CANCELED;

    void checkCancellable(){
        throw new CancellableStateException();
    }
}
