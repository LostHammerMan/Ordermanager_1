package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.order.OrderedProduct;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.request.ChangeStateRequestDto;
import kr.co.ordermanagement.presentation.dto.request.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.response.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SimpleOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderResponseDto createOrder(List<OrderProductRequestDto> requests) {

        log.info("service called.....");
        log.info("\t createOrder called...");
        for (OrderProductRequestDto dtos : requests){
            log.info("dtos.getAmount() = {}", dtos.getAmount());
        }

        // 1 requestDto 의 상품 번호(id) 에 해당하는 상품이 주문 수량만큼 재고가 있는지 확인
        List<OrderedProduct> products = makeOrderedProducts(requests);
        // 2. 재고 있다면 해당 상품의 재고 수량만큼 차감
        decreaseProductAmount(products);

        // 3. 차감된 상품 정보로 주문(Order) 생성
        Order order = Order.builder()
                .orderedProducts(products).build();

        orderRepository.add(order);
        // 4. 생성된 주문을 responseDto 로 변환하여 반환
        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(order);
        return orderResponseDto;
    }

    // id 로 주문 조회
    public OrderResponseDto findById(Long orderId) {

        Order findOrder = orderRepository.findById(orderId);
        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(findOrder);
        return orderResponseDto;
    }

    // 주문 상태 강제 변경

    // 주문 생성 메서드
    private List<OrderedProduct> makeOrderedProducts(List<OrderProductRequestDto> requestDtos){
        log.info("\t makeOrderedProducts() called.......");
        return requestDtos.stream().map(requestDto -> {
            Long productId = requestDto.getId();
            Product findProduct = productRepository.findById(productId);

            Integer findProductAmount = findProduct.getAmount();
            log.info("findProductAmount = {}", findProductAmount);
            findProduct.checkEnoughAmount(requestDto.getAmount());

            return OrderedProduct.builder()
                    .id(productId)
                    .name(findProduct.getName())
                    .price(findProduct.getPrice())
                    .amount(requestDto.getAmount())
                    .build();
        }).toList();

    }

    // 주문 수량 차감 메서드
    private void decreaseProductAmount(List<OrderedProduct> orderedProducts){
        orderedProducts.stream().forEach(orderedProduct -> {
            Long productId = orderedProduct.getId();
            Product findProduct = productRepository.findById(productId);

            Integer orderedAmount = orderedProduct.getAmount();
            findProduct.decreaseAmount(orderedAmount);

            productRepository.update(findProduct);
        });
    }


    public OrderResponseDto changeState(Long orderId, ChangeStateRequestDto request) {

        Order findOrder = orderRepository.findById(orderId);
        State state = request.getState();

        findOrder.changeState(state);
//        orderRepository.update(findOrder);

        OrderResponseDto responseDto = OrderResponseDto.toDto(findOrder);
        return responseDto;

    }

    public List<OrderResponseDto> findByState(State state) {

        List<Order> findOrders = orderRepository.findByState(state);

        List<OrderResponseDto> orderResponseDtos = findOrders.stream().map(
                order -> OrderResponseDto.toDto(order)
        ).toList();

        return orderResponseDtos;
    }

    public OrderResponseDto cancelOrderById(Long orderId) {
        Order findOrder = orderRepository.findById(orderId);
        findOrder.cancel();

        OrderResponseDto orderResponseDto = OrderResponseDto.toDto(findOrder);
        return orderResponseDto;

    }
}
