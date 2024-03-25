package kr.co.ordermanagement.presentation.dto.error;

import lombok.Getter;

@Getter
public class ErrorResponseDto {

    public String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }
}
