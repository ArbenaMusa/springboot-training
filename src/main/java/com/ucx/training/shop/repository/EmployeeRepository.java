package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByNameAndAddress(String name, String address);

    @Query(value = "SELECT * FROM employee WHERE name = :name AND address = :address", nativeQuery = true)
    List<Employee> findAllByNameAndAddressNative(@Param("name") String name, @Param("address") String address);

    @Query(value = "SELECT e FROM Employee e WHERE e.name= ?1 AND e.address = ?2")
    List<Employee> findAllByNameAndAddressJPQL(String name, String address);
}
