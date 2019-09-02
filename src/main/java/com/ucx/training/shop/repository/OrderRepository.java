package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Integer> {
    List<Order> findAllByCustomer(Customer customer);

    @Query(value ="select count(*) as Orders,sum (ORD.total) as Income,(" +
            "    select sum(CI.quantity) from cart_item CI inner join \"order\" orrd on" +
            "    orrd.id=CI.order_id where orrd.create_date_time BETWEEN ?1 AND ?2 " +
            "    ) as TotalProducts from \"order\" ORD" +
            " where ORD.create_date_time BETWEEN ?1 AND ?2" ,nativeQuery = true)
    Tuple getQuartalStats(Date startDate, Date endDate);
}
