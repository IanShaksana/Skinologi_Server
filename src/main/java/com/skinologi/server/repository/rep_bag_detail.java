package com.skinologi.server.repository;

import com.skinologi.server.table.tbl_bag_detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_bag_detail extends JpaRepository<tbl_bag_detail, String> {
    tbl_bag_detail findByIdBagAndIdInventory(String idBag, String idInventory);
    List<tbl_bag_detail> findByIdBag(String idBag);
    List<tbl_bag_detail> findByIdBagOrderByCreatedAt(String idBag);
    List<tbl_bag_detail> findByIdInventory(String idInventory);

}
