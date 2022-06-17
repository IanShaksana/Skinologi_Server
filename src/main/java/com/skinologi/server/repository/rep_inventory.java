package com.skinologi.server.repository;

import com.skinologi.server.table.tbl_inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_inventory extends JpaRepository<tbl_inventory, String> {

    @Query(value = "SELECT * FROM tbl_inventory WHERE quantity <= :a order by name", nativeQuery = true)
    List<tbl_inventory> findShort(@Param("a") Integer quantity);

    
    @Query(value = "SELECT * FROM tbl_inventory Order By created_at DESC", nativeQuery = true)
    List<tbl_inventory> findAllOrdered();

}