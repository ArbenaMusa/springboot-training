package com.ucx.training.shop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.repository.OrderRepository;
import com.ucx.training.shop.repository.ProductRepository;
import com.ucx.training.shop.type.Quartal;
import com.ucx.training.shop.util.EntityUtil;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class OrderService extends BaseService<Order, Integer> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EntityManager entityManager;

    public Order update(List<CartItem> cartItemList, Customer customer, Order order) {
        if (cartItemList == null || cartItemList.isEmpty()) {
            throw new IllegalArgumentException("Cannot print Invoice, list is missing");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Cannot print Invoice, Costumer is missing");
        }
        if (order == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }
        BigDecimal total = cartItemList
                .stream()
                .map(e -> e.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setCart(cartItemList);
        order.setCustomer(customer);
        order.setTotal(total);
        return order;
    }

    public Order print(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: " + id);
        }
        return findById(id);
    }

    public List<Order> findAllByCostumer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Invalid argument: " + customer);
        }

        return orderRepository.findAllByCustomer(customer);
    }


    public List<JsonNode> readOrderHistory(Pageable pageable) {
        StringBuilder query = new StringBuilder();
        query.append("Select row_to_json (data) as result from\n" +
                "    (select customer.name as \"customerName\", customer.id as \"customerId\",torder.id as \"orderId\",torder.create_date_time as \"orderTime\", torder.total as \"total\"\n" +
                "          ,(\n" +
                "            select array_to_json(array_agg(row_to_json(d))) as itemsPurchased\n" +
                "            from\n" +
                "                (\n" +
                "    select cart_item.product_id,product.name,cart_item.quantity from cart_item\n" +
                "    inner join product on cart_item.product_id=product.id and cart_item.order_id=torder.id\n" +
                " and cart_item.record_status like 'ACTIVE' and product.record_status like 'ACTIVE' \n" +
                "                ) d\n" +
                "        )\n" +
                "     from \"order\" torder inner join customer on torder.customer_id=customer.id \n" +
                "where torder.record_status like 'ACTIVE' and customer.record_status like 'ACTIVE'" +
                ") data limit ?1 offset ?2");
        List<JsonNode> list = entityManager.createNativeQuery(query.toString())
                .unwrap(NativeQuery.class)
                .addScalar("result", new JsonNodeBinaryType())
                .setParameter(1, pageable.getPageSize())
                .setParameter(2, pageable.getOffset())
                .getResultList();
        return list;
    }


    public EnumMap<Quartal, Map> getQuartalStats() {
        EnumMap<Quartal, Map> response = new EnumMap<>(Quartal.class);
        List<Map<String, Object>> topSoldProducts = new ArrayList<>();
        //this is where we loop for every Quartal
        for (Quartal quartal : Quartal.values()) {
            // The income,orders,and totalProducts sold Stats for the given quartal are obtained
            Map<String, Object> map = EntityUtil.toMap(orderRepository.getQuartalStats(
                    Date.from(quartal.getStartDate().atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(quartal.getEndDate().atZone(ZoneId.systemDefault()).toInstant())));
            // We obtain top sold products for the given quartal.
            // Data is obtained as a list of tuples therefore we convert tuples to maps
            // and store them in a list i.e. <topSoldProducts> variable
            productRepository.getTopSoldProducts(10,
                    Date.from(quartal.getStartDate().atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(quartal.getEndDate().atZone(ZoneId.systemDefault()).toInstant())).
                    stream().
                    forEach(e -> {
                        topSoldProducts.add(EntityUtil.toMap(e));
                    });
            //The top products sold data is taken from the topSoldProducts (List) variable
            // and appended to the stats map
            map.put("topSoldItems", new ArrayList<>(topSoldProducts));
            //the whole quartal data is put in an EnumMap response
            response.put(quartal, map);
            //topProductsSold list is cleared for the next iteration
            topSoldProducts.clear();
        }
        return response;
    }


}
