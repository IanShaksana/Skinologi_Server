package com.skinologi.server.repository;
import com.skinologi.server.table.tbl_bag;
import org.springframework.data.jpa.repository.JpaRepository;

/*
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_bag extends JpaRepository<tbl_bag, String> {
    tbl_bag findByIdPatient(String idPatient);

}
