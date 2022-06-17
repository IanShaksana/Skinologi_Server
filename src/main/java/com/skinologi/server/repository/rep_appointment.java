package com.skinologi.server.repository;

import com.skinologi.server.table.tbl_appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_appointment extends JpaRepository<tbl_appointment, String> {
    @Query(value = "SELECT * FROM tbl_appointment WHERE date <= :a AND date >= :b Order By date, time  ", nativeQuery = true)
    List<tbl_appointment> findAfter(@Param("a") Date tanggal, @Param("b") Date today);

    @Query(value = "SELECT * FROM tbl_appointment Order By created_at DESC", nativeQuery = true)
    List<tbl_appointment> findAllOrdered();

}
