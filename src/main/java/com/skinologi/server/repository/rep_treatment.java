package com.skinologi.server.repository;
import com.skinologi.server.table.tbl_treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/*
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_treatment extends JpaRepository<tbl_treatment, String> {

    
    @Query(value = "SELECT * FROM tbl_treatment Order By created_at DESC", nativeQuery = true)
    List<tbl_treatment> findAllOrdered();
}