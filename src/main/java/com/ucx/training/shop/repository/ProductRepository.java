package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Integer> {
    @Query(value = "SELECT * from product where record_status = 'ACTIVE' ", nativeQuery = true)
    Page<Product> findAllActive(Pageable pageable);
    Product findByName(String name);
    List<Product> findAllByUnitPrice(BigDecimal unitPrice);
    Page<Product> findAllProductByUnitPriceBetween(Pageable pageable, BigDecimal lowest, BigDecimal highest);
    Page<Product> findAllProductByUnitPriceBetweenAndBrandIsIn(Pageable pageable, BigDecimal lowest, BigDecimal highest, List<Brand> brand);
    Page<Product> findAllProductByUnitPriceBetweenAndPlatform(Pageable pageable, BigDecimal lowest, BigDecimal highest, Platform platform);
    Page<Product> findAllProductByUnitPriceBetweenAndBrandIsInAndPlatform(Pageable pageable, BigDecimal lowest, BigDecimal highest, List<Brand> brand, Platform platform);
    Page<Product> findAllProductByUnitPriceBetweenAndNameContainingIgnoreCase(Pageable pageable, BigDecimal lowest, BigDecimal highest, String name);
    Page<Product> findAllProductByUnitPriceBetweenAndBrandIsInAndNameContainingIgnoreCase(Pageable pageable, BigDecimal lowest, BigDecimal highest, List<Brand> brand, String name);
    Page<Product> findAllProductByUnitPriceBetweenAndPlatformAndNameContainingIgnoreCase(Pageable pageable, BigDecimal lowest, BigDecimal highest, Platform platform, String name);
    Page<Product> findAllProductByUnitPriceBetweenAndBrandIsInAndPlatformAndNameContainingIgnoreCase(Pageable pageable, BigDecimal lowest, BigDecimal highest, List<Brand> brand, Platform platform, String name);
    @Query(value = "SELECT MIN(unit_price) FROM product", nativeQuery = true)
    Number getLowestPrice();
    @Query(value = "SELECT MAX(unit_price) FROM product", nativeQuery = true)
    Number getHighestPrice();

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
            "  AND O.create_date_time BETWEEN :startDate AND :endDate\n" +
            "GROUP BY P.id, F.file_name\n" +
            "ORDER BY pcsSold Desc\n" +
            "LIMIT :productsNumber ", nativeQuery = true)
    List<Tuple> getTopSoldProducts(@Param("productsNumber") Integer productsNumber, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Page<Product> findAllByNameContainingIgnoreCase(Pageable pageable, String name);
}
