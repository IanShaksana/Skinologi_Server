package com.skinologi.server.controller;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.skinologi.server.model.body_messages.model_id;
import com.skinologi.server.model.custom_response.model_inventory_tx;
import com.skinologi.server.model.http.http_response;
import com.skinologi.server.repository.*;
import com.skinologi.server.table.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping(path = "/api/inventory")
public class inventory_controller {

    @Autowired
    private rep_inventory rep_inventory;
    @Autowired
    private rep_patient rep_patient;
    @Autowired
    private rep_inventory_tx rep_inventory_tx;
    @Autowired
    private rep_bag_detail rep_bag_detail;
    @Autowired
    private rep_bag rep_bag;

    @PostMapping(path = "/get/all")
    public ResponseEntity<http_response> getAll() {

        http_response resp = new http_response();
        try {
            // do something
            resp.setSuccessWithData(rep_inventory.findAllOrdered());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/get/short")
    public ResponseEntity<http_response> getShort() {
        log.info("/inventory/get/short");

        http_response resp = new http_response();
        try {
            List<tbl_inventory> input_data = rep_inventory.findShort(10);
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
            List<tbl_inventory> input_data = new ArrayList<>();
            input_data.add(rep_inventory.findById(model.getId()).get());
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

        http_response resp = new http_response();
        try {
            tbl_inventory model = new Gson().fromJson(message, tbl_inventory.class);
            tbl_inventory newData = new tbl_inventory(model);
            rep_inventory.save(newData);
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

        http_response resp = new http_response();
        try {
            tbl_inventory model = new Gson().fromJson(message, tbl_inventory.class);
            tbl_inventory oldData = rep_inventory.findById(model.getIdInventory()).get();
            if (oldData.getVersion() == model.getVersion()) {
                oldData.edit(model);
                rep_inventory.save(oldData);
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

    @PostMapping(path = "/tx/get/all")
    public ResponseEntity<http_response> getAllTx() {
        log.info("/api/inventory/tx/get/all");

        http_response resp = new http_response();
        try {
            List<tbl_inventory_tx> l_inventory_tx = rep_inventory_tx.findAllOrdered();
            List<model_inventory_tx> input_data = new ArrayList<>();
            for (tbl_inventory_tx inventory_tx : l_inventory_tx) {
                tbl_inventory inventory = rep_inventory.findById(inventory_tx.getIdInventory()).get();
                if (inventory_tx.getType() == 2 || inventory_tx.getType() == 3) {
                    tbl_patient patient;
                    if (inventory_tx.getType() == 2) {
                        patient = rep_patient.findById(inventory_tx.getSource()).get();
                    } else {
                        patient = rep_patient.findById(inventory_tx.getDestination()).get();
                    }
                    input_data.add(new model_inventory_tx(inventory, inventory_tx, patient));
                } else {

                    if (inventory_tx.getType() == 5 || inventory_tx.getType() == 6) {

                        // tbl_treatment_usage usage =
                        // rep_treatment_usage.findById(inventory_tx.getDestination()).get();
                        // tbl_treatment_tx tx =
                        // rep_treatment_tx.findById(usage.getIdTreatmentTx()).get();
                        // tbl_patient patient = rep_patient.findById(tx.getIdPatient()).get();
                        // tbl_patient patient =
                        // rep_patient.findById(inventory_tx.getDestination()).get();
                        // input_data.add(new model_inventory_tx(inventory, inventory_tx, patient));
                    } else {
                        input_data.add(new model_inventory_tx(inventory, inventory_tx, new tbl_patient()));
                    }

                }

            }
            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/get/detail")
    public ResponseEntity<http_response> getDetailTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_id model = new Gson().fromJson(message, model_id.class);
            List<model_inventory_tx> input_data = new ArrayList<>();
            tbl_inventory_tx inventory_tx = rep_inventory_tx.findById(model.getId()).get();
            tbl_inventory inventory = rep_inventory.findById(inventory_tx.getIdInventory()).get();
            if (inventory_tx.getType() == 2 || inventory_tx.getType() == 3) {
                tbl_patient patient;
                if (inventory_tx.getType() == 2) {
                    patient = rep_patient.findById(inventory_tx.getSource()).get();
                } else {
                    patient = rep_patient.findById(inventory_tx.getDestination()).get();
                }
                input_data.add(new model_inventory_tx(inventory, inventory_tx, patient));
            } else {
                input_data.add(new model_inventory_tx(inventory, inventory_tx));

            }
            // do something
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());
    }

    @PostMapping(path = "/tx/create")
    public ResponseEntity<http_response> createTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_inventory_tx model = new Gson().fromJson(message, tbl_inventory_tx.class);
            tbl_inventory_tx newData = new tbl_inventory_tx(model);
            rep_inventory_tx.save(newData);
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/edit")
    public ResponseEntity<http_response> editTx(@RequestBody String message) {
        log.info(message);
        http_response resp = new http_response();
        try {
            tbl_inventory_tx model = new Gson().fromJson(message, tbl_inventory_tx.class);
            tbl_inventory_tx oldData = rep_inventory_tx.findById(model.getIdInventoryTx()).get();
            if (oldData.getVersion() == model.getVersion() && oldData.getStatus().equalsIgnoreCase("DRAFT")) {
                oldData.edit(model);
                rep_inventory_tx.save(oldData);
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/delete")
    public ResponseEntity<http_response> deleteTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_inventory_tx model = new Gson().fromJson(message, tbl_inventory_tx.class);
            tbl_inventory_tx oldData = rep_inventory_tx.findById(model.getIdInventoryTx()).get();
            if (oldData.getVersion() == model.getVersion() && oldData.getStatus().equalsIgnoreCase("DRAFT")) {
                rep_inventory_tx.delete(oldData);
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/verif")
    public ResponseEntity<http_response> verifTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_inventory_tx model = new Gson().fromJson(message, tbl_inventory_tx.class);
            tbl_inventory_tx data = rep_inventory_tx.findById(model.getIdInventoryTx()).get();
            tbl_inventory inventory = rep_inventory.findById(model.getIdInventory()).get();
            tbl_bag_detail bag_detail;
            tbl_bag bag;
            if (model.getStatus().equalsIgnoreCase("DRAFT")) {
                switch (model.getType()) {

                    // add from distributor 1
                    case 1:
                        inventory.setQuantity(inventory.getQuantity() + model.getQuantity());
                        rep_inventory.save(inventory);

                        data.setStatus("FINISH");
                        rep_inventory_tx.save(data);
                        resp.setSuccess();
                        break;

                    // add from bag 2
                    case 2:
                        bag = rep_bag.findByIdPatient((model.getSource()));
                        bag_detail = rep_bag_detail.findByIdBagAndIdInventory(bag.getIdBag(),
                                model.getIdInventory());

                        if (bag_detail != null) {

                            Boolean check = (bag_detail.getQuantity() - model.getQuantity()) >= 0;
                            if (check) {
                                bag_detail.setQuantity(bag_detail.getQuantity() - model.getQuantity());
                                rep_bag_detail.save(bag_detail);

                                inventory.setQuantity(inventory.getQuantity() + model.getQuantity());
                                rep_inventory.save(inventory);

                                data.setStatus("FINISH");
                                rep_inventory_tx.save(data);
                                resp.setSuccess();
                            } else {
                                resp.setFail();
                                resp.setMessage("Bag Detail Minus");
                                // ada barangnya tapi tidak cukup
                            }

                        } else {
                            resp.setFail();
                            resp.setMessage("Bag Detail Tidak Ada");
                            // tidak ada barangnya
                        }
                        break;

                    // loss to bag 3
                    case 3:
                        Boolean check_case_3 = (inventory.getQuantity() - model.getQuantity()) >= 0;
                        if (check_case_3) {

                            inventory.setQuantity(inventory.getQuantity() - model.getQuantity());
                            rep_inventory.save(inventory);

                            bag_detail = rep_bag_detail.findByIdBagAndIdInventory(model.getSource(),
                                    model.getDestination());
                            if (bag_detail != null) {
                                bag_detail.setQuantity(bag_detail.getQuantity() + model.getQuantity());
                                rep_bag_detail.save(bag_detail);
                            } else {
                                bag = rep_bag.findByIdPatient((model.getDestination()));
                                tbl_bag_detail new_bag_detail = new tbl_bag_detail();
                                new_bag_detail.setIdBag(bag.getIdBag());
                                new_bag_detail.setIdInventory(model.getIdInventory());
                                new_bag_detail.setQuantity(model.getQuantity());
                                rep_bag_detail.save(new_bag_detail);
                            }

                            data.setStatus("FINISH");
                            rep_inventory_tx.save(data);
                            resp.setSuccess();

                        } else {

                            resp.setFail();
                            resp.setMessage("Inventory Minus");
                            // tidak cukup
                        }

                        break;

                    // loss to nothing 4
                    case 4:
                        Boolean check_case_4 = (inventory.getQuantity() - model.getQuantity()) >= 0;
                        if (check_case_4) {
                            inventory.setQuantity(inventory.getQuantity() - model.getQuantity());
                            rep_inventory.save(inventory);

                            data.setStatus("FINISH");
                            rep_inventory_tx.save(data);
                            resp.setSuccess();
                        } else {

                            resp.setFail();
                            resp.setMessage("Inventory Minus");
                            // tidak cukup
                        }

                        break;

                    case 5:
                        // used for treatment from inventory
                        bag = rep_bag.findByIdPatient((model.getDestination()));
                        Boolean check_case_5 = (inventory.getQuantity() - model.getQuantity()) >= 0;
                        if (check_case_5) {

                            inventory.setQuantity(inventory.getQuantity() - model.getQuantity());
                            rep_inventory.save(inventory);

                            bag_detail = rep_bag_detail.findByIdBagAndIdInventory(bag.getIdBag(),
                                    model.getIdInventory());
                            if (bag_detail != null) {
                                bag_detail.setQuantity(bag_detail.getQuantity() + model.getQuantity());
                                rep_bag_detail.save(bag_detail);
                            } else {
                                tbl_bag_detail new_bag_detail = new tbl_bag_detail();
                                new_bag_detail.setIdBag(bag.getIdBag());
                                new_bag_detail.setIdInventory(model.getIdInventory());
                                new_bag_detail.setQuantity(model.getQuantity());
                                rep_bag_detail.save(new_bag_detail);
                            }

                            data.setStatus("FINISH");
                            rep_inventory_tx.save(data);
                            resp.setSuccess();

                        } else {

                            resp.setFail();
                            resp.setMessage("Inventory Minus");
                            // tidak cukup
                        }

                        break;

                    case 6:
                        // used for treatment from bag
                        bag = rep_bag.findByIdPatient((model.getSource()));
                        bag_detail = rep_bag_detail.findByIdBagAndIdInventory(bag.getIdBag(),
                                model.getIdInventory());

                        if (bag_detail != null) {

                            Boolean check = (bag_detail.getQuantity() - model.getQuantity()) >= 0;
                            if (check) {
                                bag_detail.setQuantity(bag_detail.getQuantity() - model.getQuantity());
                                rep_bag_detail.save(bag_detail);

                                // inventory.setQuantity(inventory.getQuantity() + model.getQuantity());
                                // rep_inventory.save(inventory);

                                data.setStatus("FINISH");
                                rep_inventory_tx.save(data);
                                resp.setSuccess();
                            } else {
                                resp.setFail();
                                resp.setMessage("Bag Detail Minus");
                                // ada barangnya tapi tidak cukup
                            }

                        } else {
                            resp.setFail();
                            resp.setMessage("Bag Detail Tidak Ada");
                            // tidak ada barangnya
                        }
                        break;

                    default:
                        resp.setFail();
                        resp.setMessage("Unknown Command");
                        break;
                }

            } else {
                resp.setFail();
                resp.setMessage("Sudah Di Verif");
            }

            // do something
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

}