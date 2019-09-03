package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Integer> {
    @Query(value = "SELECT * from product where record_status = 'ACTIVE' ", nativeQuery = true)
    public List<Product>  findAllActive();
    Product findByName(String name);
    List<Product> findAllByUnitPrice(BigDecimal unitPrice);


    @Query(value = "select P1.id as productId,P1.name as itemName,F1.file_name as image,sum(C1.quantity) as pcsSold,P1.unit_price as unitPrice\n" +
            "from\n" +
            "   ( ((product P1 inner join cart_item C1 on P1.id=C1.product_id)\n" +
            "        inner join file_upload F1 on P1.id=F1.product_id)\n" +
            "   inner join \"order\" O1 on O1.id=C1.order_id)\n" +
            "where C1.record_status like 'ACTIVE' and P1.record_status like 'ACTIVE'\n" +
            "  and  F1.record_status like 'ACTIVE'\n" +
            "  and O1.create_date_time  BETWEEN ?2 AND ?3\n" +
            "group by P1.id,F1.file_name order by pcsSold Desc limit ?1\n", nativeQuery = true)
    List<Tuple> getTopSoldProducts(Integer productsNumber, Date startDate, Date endDate);
}
