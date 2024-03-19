package kr.co.ordermanagement.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductRequestDto {

    private Long id;
    private Integer amount;

    @Builder
    public OrderProductRequestDto(Long id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }
}
