package com.skinologi.server.repository;
import com.skinologi.server.table.tbl_treatment_tx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
/*
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_treatment_tx extends JpaRepository<tbl_treatment_tx, String> {
    List<tbl_treatment_tx> findByIdPatientOrderByCreatedAt(String idPatient);

    
    @Query(value = "SELECT * FROM tbl_treatment_tx Order By created_at DESC", nativeQuery = true)
    List<tbl_treatment_tx> findAllOrdered();
}