package com.skinologi.server.repository;

import com.skinologi.server.table.tbl_patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

/*
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_patient extends JpaRepository<tbl_patient, String> {
    @Query(value = "SELECT * FROM tbl_patient order by created_at DESC", nativeQuery = true)
    List<tbl_patient> findAllOrdered();

}