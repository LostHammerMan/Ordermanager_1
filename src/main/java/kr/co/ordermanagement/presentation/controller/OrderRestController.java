package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.domain.exception.OrderStateNotFoundException;
import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.presentation.dto.request.ChangeStateRequestDto;
import kr.co.ordermanagement.presentation.dto.request.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {

    private final SimpleOrderService simpleOrderService;

    // 상품 주문 Api
    @PostMapping("")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody List<OrderProductRequestDto> requests){
        OrderResponseDto responseDto = simpleOrderService.createOrder(requests);
        log.info("responseDto = {}", responseDto);

        return ResponseEntity.ok(responseDto);
    }

    // 주문 번호로 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId){
        OrderResponseDto responseDto = simpleOrderService.findById(orderId);
        return ResponseEntity.ok(responseDto);

    }

    // 주문 상태 강제 변경
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> changeOrderState(
            @PathVariable Long orderId, @RequestBody ChangeStateRequestDto request){

        if (request.getState().equals("CREATED") ||
                request.getState().equals("SHIPPING") ||
                request.getState().equals("COMPLETED") ||
                request.getState().equals("CANCELED")){
            OrderResponseDto responseDto = simpleOrderService.changeState(orderId, request);
            return ResponseEntity.ok(responseDto);

        }else {
            throw new OrderStateNotFoundException();
        }
    }

    // 주문 상태로 조회
    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getOrderByState(@RequestParam State state){
        List<OrderResponseDto> orderResponseDtos = simpleOrderService.findByState(state);

        return ResponseEntity.ok(orderResponseDtos);
    }

    // 주문 취소 api
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrderById(@PathVariable Long orderId){
        OrderResponseDto orderResponseDto = simpleOrderService.cancelOrderById(orderId);
        return ResponseEntity.ok(orderResponseDto);

    }
}
