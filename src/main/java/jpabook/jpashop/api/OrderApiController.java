package jpabook.jpashop.api;

import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("api/orders")
    public Result list(@RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "100") int limit) {
        //return new Result(orderService.getOrderDtoList());
        return new Result(orderService.getOrderList(offset, limit));
    }

    @AllArgsConstructor
    @Data
    private class Result<T> {
        private T data;
    }
}
