package io.ggammu.realspringbootjpa.repository;

import io.ggammu.realspringbootjpa.domain.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * JPQL Query
     * @param orderSearch
     * @return
     */
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o from Order o join fetch o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += "where ";
                isFirstCondition = false;
            }
            jpql += "o.status = :status ";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += "where ";
            } else {
                jpql += "and ";
            }
            jpql += "m.name like :name ";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class);

        if (orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * JPA Criteria
     * @param orderSearch
     * @return
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    /**
     * QueryDSL
     * @param orderSearch
     * @return
     */
    public List<Order> findAllByQueryDsl(OrderSearch orderSearch) {

        return new ArrayList<>();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o " +
                "join fetch o.member m " +
                "join fetch o.delivery d ", Order.class).getResultList();
    }


    public List<Order> findAllWithItem(OrderSearch orderSearch) {
        return em.createQuery(
                "select distinct o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d " +
                        "join fetch o.orderItems oi " +
                        "join fetch oi.item i ", Order.class)
                .getResultList();
    }

}
