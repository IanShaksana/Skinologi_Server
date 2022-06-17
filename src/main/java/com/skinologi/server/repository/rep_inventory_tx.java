package com.skinologi.server.repository;
import com.skinologi.server.table.tbl_inventory_tx;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/*
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_inventory_tx extends JpaRepository<tbl_inventory_tx,String> {
    List<tbl_inventory_tx> findBySource(String source);
    List<tbl_inventory_tx> findByDestination(String destination);

    
    @Query(value = "SELECT * FROM tbl_inventory_tx Order By created_at DESC", nativeQuery = true)
    List<tbl_inventory_tx> findAllOrdered();

}
