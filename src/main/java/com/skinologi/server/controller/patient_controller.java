package com.skinologi.server.controller;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skinologi.server.model.custom_response.model_bag_detail_inventory;
import com.skinologi.server.model.body_messages.model_id;
import com.skinologi.server.model.custom_response.model_appointment_patient;
import com.skinologi.server.model.http.http_response;
import com.skinologi.server.repository.rep_appointment;
import com.skinologi.server.repository.rep_bag;
import com.skinologi.server.repository.rep_bag_detail;
import com.skinologi.server.repository.rep_inventory;
import com.skinologi.server.repository.rep_patient;
import com.skinologi.server.table.tbl_appointment;
import com.skinologi.server.table.tbl_bag;
import com.skinologi.server.table.tbl_bag_detail;
import com.skinologi.server.table.tbl_inventory;
import com.skinologi.server.table.tbl_patient;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping(path = "/api/patient")
public class patient_controller {

    @Autowired
    private rep_patient rep_patient;
    @Autowired
    private rep_bag rep_bag;
    @Autowired
    private rep_bag_detail rep_bag_detail;
    @Autowired
    private rep_appointment rep_appointment;
    @Autowired
    private rep_inventory rep_inventory;

    @PostMapping(path = "/get/all")
    public ResponseEntity<http_response> getAll() {

        log.info("/api/patient/get/all");
        http_response resp = new http_response();
        try {
            // do something
            resp.setSuccessWithData(rep_patient.findAllOrdered());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/get/bag")
    public ResponseEntity<http_response> getBag(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_id model = new Gson().fromJson(message, model_id.class);
            tbl_patient patient = rep_patient.findById(model.getId()).get();
            tbl_bag bag = rep_bag.findByIdPatient(patient.getIdPatient());
            List<tbl_bag_detail> l_bag_detail = rep_bag_detail.findByIdBagOrderByCreatedAt(bag.getIdBag());

            // remodel
            List<model_bag_detail_inventory> input_data = new ArrayList<>();
            for (tbl_bag_detail bagDetail : l_bag_detail) {
                tbl_inventory inventory = rep_inventory.findById(bagDetail.getIdInventory()).get();
                input_data.add(new model_bag_detail_inventory(bagDetail, inventory));
            }
            // remodel

            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/get/detail")
    public ResponseEntity<http_response> getDetail(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_id model = new Gson().fromJson(message, model_id.class);
            List<tbl_patient> input_data = new ArrayList<>();
            input_data.add(rep_patient.findById(model.getId()).get());
            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<http_response> create(@RequestBody String message) {
        log.info("/api/patient/create");
        log.info(message);

        http_response resp = new http_response();
        try {
            // create patient
            tbl_patient model = new Gson().fromJson(message, tbl_patient.class);
            tbl_patient newData = new tbl_patient(model);
            rep_patient.save(newData);

            // create bag
            tbl_bag bag = new tbl_bag();
            bag.setIdPatient(newData.getIdPatient());
            rep_bag.save(bag);

            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/edit")
    public ResponseEntity<http_response> edit(@RequestBody String message) {
        log.info("/api/patient/edit");
        log.info(message);
        http_response resp = new http_response();
        try {
            tbl_patient model = new Gson().fromJson(message, tbl_patient.class);
            tbl_patient oldData = rep_patient.findById(model.getIdPatient()).get();
            if (oldData.getVersion() == model.getVersion()) {
                oldData.edit(model);
                rep_patient.save(oldData);
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    // unused
    @PostMapping(path = "/delete")
    public ResponseEntity<http_response> delete(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/appointment/get/all")
    public ResponseEntity<http_response> getAllAppointment() {

        http_response resp = new http_response();
        try {
            // do something
            List<tbl_appointment> l_appointment = rep_appointment.findAllOrdered();
            // remodel
            List<model_appointment_patient> input_data = new ArrayList<>();
            for (tbl_appointment appointment : l_appointment) {
                tbl_patient patient = rep_patient.findById(appointment.getIdPatient()).get();
                input_data.add(new model_appointment_patient(appointment, patient));
            }
            // remodel

            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/appointment/get/short")
    public ResponseEntity<http_response> getShortAppointment() {
        log.info("/appointment/get/short");
        http_response resp = new http_response();
        try {

            LocalDate date = new DateTime(new Date()).toLocalDate().plusDays(10);
            LocalDate today = new DateTime(new Date()).toLocalDate();
            List<tbl_appointment> l_appointment = rep_appointment.findAfter(date.toDate(), today.toDate());

            // remodel
            List<model_appointment_patient> input_data = new ArrayList<>();
            for (tbl_appointment appointment : l_appointment) {
                tbl_patient patient = rep_patient.findById(appointment.getIdPatient()).get();
                input_data.add(new model_appointment_patient(appointment, patient));
            }
            // remodel

            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/appointment/get/detail")
    public ResponseEntity<http_response> getDetailAppointment(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_id model = new Gson().fromJson(message, model_id.class);
            List<tbl_appointment> input_data = new ArrayList<>();
            input_data.add(rep_appointment.findById(model.getId()).get());
            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());
    }

    @PostMapping(path = "/appointment/create")
    public ResponseEntity<http_response> createAppointment(@RequestBody String message) {
        
        log.info("/Appointment/create");
        log.info(message);

        http_response resp = new http_response();
        try {
            // create patient
            tbl_appointment model = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create().fromJson(message, tbl_appointment.class);
            tbl_appointment newData = new tbl_appointment(model);
            rep_appointment.save(newData);

            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/appointment/edit")
    public ResponseEntity<http_response> editAppointment(@RequestBody String message) {
        
        log.info("/Appointment/edit");
        log.info(message);

        http_response resp = new http_response();
        try {
            tbl_appointment model = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create().fromJson(message, tbl_appointment.class);
            tbl_appointment oldData = rep_appointment.findById(model.getIdAppointment()).get();
            if (oldData.getVersion() == model.getVersion()) {
                oldData.edit(model);
                rep_appointment.save(oldData);
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    // unused
    @PostMapping(path = "/appointment/delete")
    public ResponseEntity<http_response> deleteAppointment(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_appointment model = new Gson().fromJson(message, tbl_appointment.class);
            tbl_appointment oldData = rep_appointment.findById(model.getIdAppointment()).get();
            if (oldData.getVersion() == model.getVersion()) {
                rep_appointment.delete(oldData);
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

}