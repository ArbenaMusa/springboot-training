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

    @Query(value ="select count(*) as Orders,coalesce (sum(ORD.total),0) as Income,coalesce (( " +
            "    select sum(CI.quantity) from cart_item CI inner join \"order\" orrd on " +
            "    orrd.id=CI.order_id where orrd.create_date_time BETWEEN ?1 AND ?2 " +
            "   and CI.record_status like 'ACTIVE' and orrd.record_status like 'ACTIVE' " +
            " ),0) as TotalProducts from \"order\" ORD " +
            " where ORD.create_date_time BETWEEN ?1 AND ?2" +
            " and ORD.record_status like 'ACTIVE' " ,nativeQuery = true)
    Tuple getQuartalStats(Date startDate, Date endDate);

}
