package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT role_id, p.module as module, p.action as action\n" +
            "FROM permission_role\n" +
            "INNER JOIN permission p on permission_role.permission_id = p.id\n" +
            "WHERE role_id = :id", nativeQuery = true)
    List<Tuple> readAllByRole(@Param("id") Integer id);
}
