package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Costumer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CostumerRepository extends BaseRepository<Costumer,Integer> {
    List<Costumer> findAllByName(String name);

    @Query(value = "SELECT c.id AS id, c.name AS name, a.street AS street, a.zip_code AS zip_code, a.country AS country FROM costumer c " +
            "INNER JOIN address a ON c.id = a.costumer_id " +
            "WHERE c.id = :id", nativeQuery = true)
    public Tuple readCostumerById(@Param("id") Integer id);
}
