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
import org.springframework.web.bind.annotation.RequestParam;

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
        BigDecimal total = calculateTotal(cartItemList);
        order.setCart(cartItemList);
        order.setCustomer(customer);
        order.setTotal(total);
        return order;
    }

    private BigDecimal calculateTotal(List<CartItem> cartItemList) {
        return cartItemList
                .stream()
                .map(e -> e.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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


    public List<JsonNode> readOrderHistory(Pageable pageable, String customerId, String orderId, String customerName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT row_to_json(orderhistorytable) AS result\n")
                .append("FROM   (\n")
                .append("  SELECT   \"totalFilteredOrders\",\n")
                .append("   (\n")
                .append("   SELECT array_to_json(array_agg(row_to_json(data)))) AS \"filteredOrdersPage\"\n")
                .append("   FROM     (\n")
                .append("   SELECT     customer.NAME           AS \"customerName\" ,\n")
                .append("   customer.id             AS \"customerId\" ,\n")
                .append("   torder.id               AS \"orderId\" ,\n")
                .append("   torder.create_date_time AS \"orderTime\" ,\n")
                .append("   torder.total            AS \"total\" ,\n")
                .append("   Count(*) OVER()         AS \"totalFilteredOrders\" ,\n")
                .append("   (SELECT array_to_json(array_agg(row_to_json(d))) AS itemspurchased\n")
                .append("   FROM   ( SELECT     cart_item.product_id,\n")
                .append("   product.NAME,\n")
                .append("   cart_item.quantity\n")
                .append("   FROM       cart_item\n")
                .append("   INNER JOIN product\n")
                .append("   ON         cart_item.product_id = product.id\n")
                .append("   AND        cart_item.order_id = torder.id\n")
                .append("   AND        cart_item.record_status LIKE 'ACTIVE'\n")
                .append("   AND        product.record_status LIKE 'ACTIVE' ) d )\n")
                .append("   FROM       \"order\" torder\n")
                .append("   INNER JOIN customer\n")
                .append("   ON         torder.customer_id = customer.id\n")
                .append("   WHERE      torder.record_status LIKE 'ACTIVE'\n")
                .append("   AND        customer.record_status LIKE 'ACTIVE'\n");


        if (customerId != null && !customerId.trim().equals("null") && !customerId.trim().equals("")) {
            query.append(" and customer.id = " + customerId + " ");
        } else if (orderId != null && !orderId.trim().equals("null") && !orderId.trim().equals("")) {
            query.append(" and torder.id =" + orderId + " ");
        } else if (customerName != null && !customerName.trim().equals("null") && !customerName.trim().equals("")) {
            query.append(" and lower(customer.name) like '" + customerName.toLowerCase() + "%' ");
        }
        query.append(" ORDER BY torder.id ASC limit ?1 offset ?2 ) data\n")
                .append(" GROUP BY \"totalFilteredOrders\" ) orderhistorytable; ");

        List<JsonNode> list = entityManager.createNativeQuery(query.toString())
                .unwrap(NativeQuery.class)
                .addScalar("result", new JsonNodeBinaryType())
                .setParameter(1, pageable.getPageSize())
                .setParameter(2, pageable.getOffset())
                .getResultList();
        return list;
    }

    public List<JsonNode> readOrderHistoryByCustomer(Pageable pageable, Integer customerId) {
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
                "where torder.record_status like 'ACTIVE' and customer.record_status like 'ACTIVE' and customer.id = " + customerId +
                ") data limit ?1 offset ?2");
        List<JsonNode> list = entityManager.createNativeQuery(query.toString())
                .unwrap(NativeQuery.class)
                .addScalar("result", new JsonNodeBinaryType())
                .setParameter(1, pageable.getPageSize())
                .setParameter(2, pageable.getOffset())
                .getResultList();
        return list;
    }


    public Map<String, Object> getQuartalStats() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> topSoldProducts = new ArrayList<>();
        //this is where we loop for every Quartal
        for (Quartal quartal : Quartal.values()) {
            // The income,orders,and totalProducts sold Stats for the given quartal are obtained
            Map<String, Object> map = EntityUtil.toMap(orderRepository.getQuartalStats(
                    Date.from(quartal.getStartDate().atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(quartal.getEndDate().atZone(ZoneId.systemDefault()).toInstant())));
            //the whole quartal data is put in an EnumMap response
            response.put(quartal.name(), map);
        }
        // We obtain top sold products for the whole year
        // Data is obtained as a list of tuples therefore we convert tuples to maps
        // and store them in a list i.e. <topSoldProducts> variable
        productRepository.getTopSoldProducts(10,
                Date.from(Quartal.FIRST_QUARTAL.getStartDate().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(Quartal.FOURTH_QUARTAL.getEndDate().atZone(ZoneId.systemDefault()).toInstant())).
                stream().
                forEach(e -> {
                    topSoldProducts.add(EntityUtil.toMap(e));
                });
        //The top products sold data is appended to the response
        response.put("topSoldItems", topSoldProducts);
        //response is returned
        return response;
    }


}
