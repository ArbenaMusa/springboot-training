package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Platform;
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
    List<Product> findAllByBrand(Brand brand);
    List<Product> findAllByPlatform(Platform platform);
    List<Product> findAllByPlatformAndBrand(Platform platform, Brand brand);

    @Query(value = "SELECT P.id             AS productId,\n" +
            "       P.name           AS productName,\n" +
            "       F.file_name      AS image,\n" +
            "       sum(CI.quantity) AS pcsSold,\n" +
            "       P.unit_price     AS unitPrice\n" +
            "FROM (((product P INNER JOIN cart_item CI ON P.id = CI.product_id)\n" +
            "    INNER JOIN file_upload F ON P.id = F.product_id)\n" +
            "         INNER JOIN \"order\" O ON O.id = CI.order_id)\n" +
            "WHERE CI.record_status = 'ACTIVE'\n" +
            "  AND P.record_status = 'ACTIVE'\n" +
            "  AND F.record_status = 'ACTIVE'\n" +
            "  AND O.record_status = 'ACTIVE'\n" +
            "  AND O.create_date_time BETWEEN ?2 AND ?3\n" +
            "GROUP BY P.id, F.file_name\n" +
            "ORDER BY pcsSold Desc\n" +
            "LIMIT ?1 ", nativeQuery = true)
    List<Tuple> getTopSoldProducts(Integer productsNumber, Date startDate, Date endDate);
}
