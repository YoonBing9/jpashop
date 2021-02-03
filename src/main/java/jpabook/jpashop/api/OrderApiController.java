package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.SelectOrderListOutDto;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("api/orders")
    public ResponseEntity<Result> list() {
        return ResponseEntity.ok(new Result(orderService.getAllOrders().stream()
                .map(SelectOrderListOutDto::new)
                .collect(toList())));
    }

    @AllArgsConstructor
    @Setter @Getter
    class Result<T> {
        private T data;
    }
}
