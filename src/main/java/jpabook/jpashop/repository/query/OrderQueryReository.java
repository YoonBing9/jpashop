package jpabook.jpashop.repository.query;

import jpabook.jpashop.repository.query.dto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderQueryReository {
    private final EntityManager em;

    public List<OrderQueryDto> findAllToDto() {
        return em.createQuery("select new jpabook.jpashop.repository.query.dto.OrderQueryDto(" +
                "o.id, m.name, o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", OrderQueryDto.class).getResultList();
    }
}
