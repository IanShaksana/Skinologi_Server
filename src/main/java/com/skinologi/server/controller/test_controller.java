package com.skinologi.server.controller;

import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.Date;

import com.skinologi.server.model.http.http_response;
import com.skinologi.server.repository.rep_test;
import com.skinologi.server.table.tbl_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api/test")
public class test_controller {

    @Autowired
    private rep_test rep_test;

    @PostMapping(path = "/1")
    public ResponseEntity<http_response> test1() {

        http_response resp = new http_response();
        try {
            Date date1 = new Date();
            LocalDate date2 = LocalDate.now();
            LocalDateTime date3 = LocalDateTime.now();
            ZonedDateTime date4 = ZonedDateTime.now(ZoneOffset.UTC);
            // date3.to
            // ZonedDateTime date3 =
            System.out.println(date1);
            System.out.println(date2);
            System.out.println(date3);
            System.out.println(date4);

            tbl_test newRow = new tbl_test();
            newRow.setDate1(date1);
            newRow.setDate2(date2);
            newRow.setDate3(date3);
            newRow.setDate4(date4);

            rep_test.save(newRow);
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/1/out")
    public ResponseEntity<http_response> test1out() {

        http_response resp = new http_response();
        try {

            // do something
            resp.setSuccessWithData(rep_test.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

}
