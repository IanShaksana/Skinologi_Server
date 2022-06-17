package com.skinologi.server.repository;

import com.skinologi.server.table.tbl_treatment_detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_treatment_detail extends JpaRepository<tbl_treatment_detail, String> {
    List<tbl_treatment_detail> findByIdTreatmentTx(String idTreatmentTx);
}