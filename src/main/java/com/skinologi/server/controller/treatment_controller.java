package com.skinologi.server.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.skinologi.server.model.body_messages.model_id;
import com.skinologi.server.model.body_messages.model_list_treatment;
import com.skinologi.server.model.custom_response.model_treatment__tx_usage_inventory;
import com.skinologi.server.model.custom_response.model_treatment_tx_treatment_detail;
import com.skinologi.server.model.http.http_response;
import com.skinologi.server.repository.*;
import com.skinologi.server.table.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/treatment")
public class treatment_controller {

    @Autowired
    private rep_patient rep_patient;

    @Autowired
    private rep_treatment rep_treatment;
    @Autowired
    private rep_treatment_tx rep_treatment_tx;
    @Autowired
    private rep_treatment_detail rep_treatment_detail;
    @Autowired
    private rep_treatment_usage rep_treatment_usage;

    @Autowired
    private rep_inventory rep_inventory;
    @Autowired
    private rep_inventory_tx rep_inventory_tx;
    @Autowired
    private rep_bag rep_bag;
    @Autowired
    private rep_bag_detail rep_bag_detail;
    @Autowired
    private inventory_controller inventory_controller;

    @PostMapping(path = "/get/all")
    public ResponseEntity<http_response> getAll() {

        http_response resp = new http_response();
        try {
            // do something
            resp.setSuccessWithData(rep_treatment.findAllOrdered());
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
            List<tbl_treatment> input_data = new ArrayList<>();
            input_data.add(rep_treatment.findById(model.getId()).get());
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
            tbl_treatment model = new Gson().fromJson(message, tbl_treatment.class);
            tbl_treatment newData = new tbl_treatment(model);
            rep_treatment.save(newData);
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
            tbl_treatment model = new Gson().fromJson(message, tbl_treatment.class);
            tbl_treatment oldData = rep_treatment.findById(model.getIdTreatment()).get();
            if (oldData.getVersion() == model.getVersion()) {
                oldData.edit(model);
                rep_treatment.save(oldData);
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

        http_response resp = new http_response();
        try {
            // do something
            List<model_treatment_tx_treatment_detail> input_data = new ArrayList<>();
            List<tbl_treatment_tx> l_treatment_tx = rep_treatment_tx.findAllOrdered();
            for (tbl_treatment_tx treatmentTx : l_treatment_tx) {
                model_treatment_tx_treatment_detail data = new model_treatment_tx_treatment_detail();
                tbl_patient patient = rep_patient.findById(treatmentTx.getIdPatient()).get();
                List<tbl_treatment> treatment = new ArrayList<>();
                List<tbl_treatment_detail> treatmentDetail = rep_treatment_detail
                        .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
                for (tbl_treatment_detail detail : treatmentDetail) {
                    treatment.add(rep_treatment.findById(detail.getIdTreatment()).get());
                }
                List<tbl_treatment_usage> treatmentUsage = rep_treatment_usage
                        .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
                data.setTreatmentTx(treatmentTx);
                data.setPatient(patient);
                data.setTreatment(treatment);
                data.setTreatmentDetail(treatmentDetail);
                data.setTreatmentUsage(treatmentUsage);
                input_data.add(data);
            }

            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());
    }

    @PostMapping(path = "/tx/get/all/by/person")
    public ResponseEntity<http_response> getAllByPersonTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_id model = new Gson().fromJson(message, model_id.class);
            List<model_treatment_tx_treatment_detail> input_data = new ArrayList<>();
            tbl_patient patient = rep_patient.findById(model.getId()).get();
            List<tbl_treatment_tx> l_treatment_tx = rep_treatment_tx.findByIdPatientOrderByCreatedAt(model.getId());

            for (tbl_treatment_tx treatmentTx : l_treatment_tx) {
                model_treatment_tx_treatment_detail data = new model_treatment_tx_treatment_detail();
                List<tbl_treatment> treatment = new ArrayList<>();
                List<tbl_treatment_detail> treatmentDetail = rep_treatment_detail
                        .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
                for (tbl_treatment_detail detail : treatmentDetail) {
                    treatment.add(rep_treatment.findById(detail.getIdTreatment()).get());
                }
                List<tbl_treatment_usage> treatmentUsage = rep_treatment_usage
                        .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
                data.setTreatmentTx(treatmentTx);
                data.setPatient(patient);
                data.setTreatment(treatment);
                data.setTreatmentDetail(treatmentDetail);
                data.setTreatmentUsage(treatmentUsage);
                if(treatmentTx.getStatus().equalsIgnoreCase("COMPLETE")){
                    input_data.add(data);
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
            List<model_treatment_tx_treatment_detail> input_data = new ArrayList<>();

            tbl_treatment_tx treatmentTx = rep_treatment_tx.findById(model.getId()).get();
            model_treatment_tx_treatment_detail data = new model_treatment_tx_treatment_detail();
            tbl_patient patient = rep_patient.findById(treatmentTx.getIdPatient()).get();
            List<tbl_treatment> treatment = new ArrayList<>();
            List<tbl_treatment_detail> treatmentDetail = rep_treatment_detail
                    .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
            for (tbl_treatment_detail detail : treatmentDetail) {
                treatment.add(rep_treatment.findById(detail.getIdTreatment()).get());
            }
            List<tbl_treatment_usage> treatmentUsage = rep_treatment_usage
                    .findByIdTreatmentTx(treatmentTx.getIdTreatmentTx());
            data.setTreatmentTx(treatmentTx);
            data.setPatient(patient);
            data.setTreatment(treatment);
            data.setTreatmentDetail(treatmentDetail);
            data.setTreatmentUsage(treatmentUsage);
            input_data.add(data);

            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/create")
    public ResponseEntity<http_response> createTx(@RequestBody String message) {
        log.info(message);

        http_response resp = new http_response();
        try {
            model_list_treatment model = new Gson().fromJson(message, model_list_treatment.class);
            tbl_treatment_tx tx = model.getTreatmentTx();
            tbl_treatment_tx newData = new tbl_treatment_tx(tx);
            rep_treatment_tx.save(newData);

            List<tbl_treatment> l_treatment = model.getTreatment();
            for (tbl_treatment treatment : l_treatment) {
                tbl_treatment_detail newDetail = new tbl_treatment_detail();
                newDetail.setIdTreatmentTx(newData.getIdTreatmentTx());
                newDetail.setIdTreatment(treatment.getIdTreatment());
                rep_treatment_detail.save(newDetail);
            }
            List<tbl_inventory_tx> l_inventory_tx = model.getInventoryTx();
            for (tbl_inventory_tx inventory : l_inventory_tx) {
                tbl_treatment_usage usage = new tbl_treatment_usage();
                usage.setIdInventory(inventory.getIdInventory());
                usage.setIdTreatmentTx(newData.getIdTreatmentTx());
                usage.setTotalQuantity(inventory.getQuantity());
                usage.setUseQuantity(0);
                rep_treatment_usage.save(usage);
            }

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

        http_response resp = new http_response();
        try {

            model_list_treatment model = new Gson().fromJson(message, model_list_treatment.class);
            tbl_treatment_tx tx = model.getTreatmentTx();
            tbl_treatment_tx newData = new tbl_treatment_tx(tx);
            rep_treatment_tx.save(newData);

            tbl_treatment_tx data = model.getTreatmentTx();
            tbl_treatment_tx oldData = rep_treatment_tx.findById(data.getIdTreatmentTx()).get();
            if (oldData.getVersion() == data.getVersion() && data.getStatus().equalsIgnoreCase("DRAFT")) {
                oldData.edit(data);
                rep_treatment_tx.save(oldData);
            }

            // delete
            List<tbl_treatment_detail> l_detail = rep_treatment_detail
                    .findByIdTreatmentTx(data.getIdTreatmentTx());
            rep_treatment_detail.deleteAll(l_detail);

            List<tbl_treatment_usage> l_usage = rep_treatment_usage
                    .findByIdTreatmentTx(data.getIdTreatmentTx());
            rep_treatment_usage.deleteAll(l_usage);

            // repopulate
            model_list_treatment treatments = new Gson().fromJson(message, model_list_treatment.class);
            List<tbl_treatment> l_treatment = treatments.getTreatment();
            for (tbl_treatment treatment : l_treatment) {
                tbl_treatment_detail newDetail = new tbl_treatment_detail();
                newDetail.setIdTreatmentTx(oldData.getIdTreatmentTx());
                newDetail.setIdTreatment(treatment.getIdTreatment());
                rep_treatment_detail.save(newDetail);
            }

            List<tbl_inventory_tx> l_inventory_tx = model.getInventoryTx();
            for (tbl_inventory_tx inventory : l_inventory_tx) {
                tbl_treatment_usage usage = new tbl_treatment_usage();
                usage.setIdInventory(inventory.getIdInventory());
                usage.setIdTreatmentTx(oldData.getIdTreatmentTx());
                usage.setTotalQuantity(inventory.getQuantity());
                usage.setUseQuantity(0);
                rep_treatment_usage.save(usage);
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
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);
            if (!model.getStatus().equalsIgnoreCase("FINISH")
                    && !model.getStatus().equalsIgnoreCase("CALCULATED")
                    && !model.getStatus().equalsIgnoreCase("COMPLETE")) {
                tbl_treatment_tx oldData = rep_treatment_tx.findById(model.getIdTreatmentTx()).get();
                if (oldData.getVersion() == model.getVersion()) {
                    List<tbl_treatment_detail> l_detail = rep_treatment_detail
                            .findByIdTreatmentTx(model.getIdTreatmentTx());
                    rep_treatment_detail.deleteAll(l_detail);

                    List<tbl_treatment_usage> l_usage = rep_treatment_usage
                            .findByIdTreatmentTx(model.getIdTreatmentTx());

                    for (tbl_treatment_usage tbl_treatment_usage : l_usage) {
                        List<tbl_inventory_tx> l_add = rep_inventory_tx
                                .findBySource(tbl_treatment_usage.getIdTreatmentUsage());
                        rep_inventory_tx.deleteAll(l_add);
                        List<tbl_inventory_tx> l_diminish = rep_inventory_tx
                                .findByDestination(tbl_treatment_usage.getIdTreatmentUsage());
                        rep_inventory_tx.deleteAll(l_diminish);

                    }
                    rep_treatment_usage.deleteAll(l_usage);
                    rep_treatment_tx.delete(oldData);

                }
            } else {
                resp.setFail();
                resp.setMessage("SUDAH FINISH");
            }
            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/usage/get")
    public ResponseEntity<http_response> getUsageTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);
            tbl_treatment_tx treatmentTx = model;
            tbl_patient patient = rep_patient.findById(treatmentTx.getIdPatient()).get();
            List<tbl_treatment_usage> l_usage = rep_treatment_usage
                    .findByIdTreatmentTx(model.getIdTreatmentTx());
            List<model_treatment__tx_usage_inventory> input_data = new ArrayList<>();
            for (tbl_treatment_usage treatmentUsage : l_usage) {
                model_treatment__tx_usage_inventory data = new model_treatment__tx_usage_inventory();
                tbl_inventory inventory = rep_inventory.findById(treatmentUsage.getIdInventory()).get();
                data.setTreatmentTx(treatmentTx);
                data.setTreatmentUsage(treatmentUsage);
                data.setPatient(patient);
                data.setInventory(inventory);
                input_data.add(data);
            }

            // do something
            resp.setSuccessWithData(input_data);
            ;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/usage/verif1")
    public ResponseEntity<http_response> verif1UsageTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_treatment_tx_treatment_detail model = new Gson().fromJson(message,
                    model_treatment_tx_treatment_detail.class);
            if (!model.getTreatmentTx().getStatus().equalsIgnoreCase("FINISH")
                    && !model.getTreatmentTx().getStatus().equalsIgnoreCase("CALCULATED")
                    && !model.getTreatmentTx().getStatus().equalsIgnoreCase("COMPLETE")) {
                List<tbl_treatment_usage> l_usage = model.getTreatmentUsage();
                rep_treatment_usage.saveAll(l_usage);

                for (tbl_treatment_usage tbl_treatment_usage : l_usage) {
                    List<tbl_inventory_tx> l_add = rep_inventory_tx
                            .findBySource(tbl_treatment_usage.getIdTreatmentUsage());
                    rep_inventory_tx.deleteAll(l_add);
                    List<tbl_inventory_tx> l_diminish = rep_inventory_tx
                            .findByDestination(tbl_treatment_usage.getIdTreatmentUsage());
                    rep_inventory_tx.deleteAll(l_diminish);

                }

                tbl_treatment_tx treatmentTx = model.getTreatmentTx();
                treatmentTx.setStatus("VERIF1");
                rep_treatment_tx.save(treatmentTx);

            } else {
                resp.setFail();
                resp.setMessage("SUDAH FINISH");
            }

            // do something
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/usage/verif2")
    public ResponseEntity<http_response> verif2UsageTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);

            if (model.getStatus().equalsIgnoreCase("VERIF1")) {
                List<tbl_treatment_usage> l_usage = rep_treatment_usage
                        .findByIdTreatmentTx(model.getIdTreatmentTx());
                tbl_bag bag = rep_bag.findByIdPatient((model.getIdPatient()));
                for (tbl_treatment_usage usage : l_usage) {

                    tbl_bag_detail bag_detail = rep_bag_detail.findByIdBagAndIdInventory(bag.getIdBag(),
                            usage.getIdInventory());

                    if (bag_detail != null) {
                        Integer quantity_bag = bag_detail.getQuantity();
                        Integer quantity_total = usage.getTotalQuantity();

                        Boolean check_1 = quantity_total > quantity_bag;
                        Boolean check_2 = quantity_total < quantity_bag;
                        Boolean check_3 = quantity_total == quantity_bag;

                        if (check_1) {
                            tbl_inventory_tx tx1 = new tbl_inventory_tx();
                            tx1.add_total_minimum(usage, model);
                            tx1.setQuantity(quantity_total - quantity_bag);
                            rep_inventory_tx.save(tx1);

                            tbl_inventory_tx tx2 = new tbl_inventory_tx();
                            tx2.diminish_by_usage(usage, model);
                            rep_inventory_tx.save(tx2);

                            usage.setRealQuantity(tx1.getQuantity());
                            rep_treatment_usage.save(usage);
                        }

                        if (check_2) {
                            tbl_inventory_tx tx2 = new tbl_inventory_tx();
                            tx2.diminish_by_usage(usage, model);
                            rep_inventory_tx.save(tx2);
                        }

                        if (check_3) {
                            tbl_inventory_tx tx2 = new tbl_inventory_tx();
                            tx2.diminish_by_usage(usage, model);
                            rep_inventory_tx.save(tx2);
                        }

                    } else {
                        tbl_inventory_tx tx1 = new tbl_inventory_tx();
                        tx1.add_total_minimum(usage, model);
                        rep_inventory_tx.save(tx1);

                        tbl_inventory_tx tx2 = new tbl_inventory_tx();
                        tx2.diminish_by_usage(usage, model);
                        rep_inventory_tx.save(tx2);

                        usage.setRealQuantity(tx1.getQuantity());
                        rep_treatment_usage.save(usage);
                    }

                    // you add the total first to bag until minumum qouta
                    // you diminish by usage

                }

                model.setStatus("VERIF2");
                rep_treatment_tx.save(model);
                resp.setSuccess();
            } else {
                resp.setFail();
                resp.setMessage("HARUS VERIF 1 DULU");
            }

            // do something
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/verif")
    public ResponseEntity<http_response> verif1Tx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);

            if (model.getStatus().equalsIgnoreCase("VERIF2")) {

                List<tbl_treatment_usage> l_usage = rep_treatment_usage.findByIdTreatmentTx(model.getIdTreatmentTx());

                for (tbl_treatment_usage tbl_treatment_usage : l_usage) {
                    List<tbl_inventory_tx> l_add = rep_inventory_tx
                            .findBySource(tbl_treatment_usage.getIdTreatmentUsage());
                    for (tbl_inventory_tx tx : l_add) {
                        String jsonString = new Gson().toJson(tx);
                        inventory_controller.verifTx(jsonString);
                    }
                    List<tbl_inventory_tx> l_diminish = rep_inventory_tx
                            .findByDestination(tbl_treatment_usage.getIdTreatmentUsage());
                    for (tbl_inventory_tx tx : l_diminish) {
                        String jsonString = new Gson().toJson(tx);
                        inventory_controller.verifTx(jsonString);
                    }

                }

                model.setStatus("FINISH");
                rep_treatment_tx.save(model);

                resp.setSuccess();
            } else {
                resp.setFail();
                resp.setMessage("HARUS VERIF 2 DULU");
            }

            // do something
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/calculate")
    public ResponseEntity<http_response> calculateTx(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);

            if (model.getStatus().equalsIgnoreCase("FINISH") || model.getStatus().equalsIgnoreCase("CALCULATED")) {
                // list all treatment
                List<tbl_treatment_detail> l_treatment = rep_treatment_detail
                        .findByIdTreatmentTx(model.getIdTreatmentTx());
                BigDecimal treatmentPrice = new BigDecimal("0.00");
                for (tbl_treatment_detail e : l_treatment) {
                    tbl_treatment treatment = rep_treatment.findById(e.getIdTreatment()).get();
                    treatmentPrice = treatmentPrice.add(treatment.getPrice());
                }
                model.setTreatmentPrice(treatmentPrice);

                // list all inventory
                List<tbl_treatment_usage> l_usage = rep_treatment_usage.findByIdTreatmentTx(model.getIdTreatmentTx());
                BigDecimal inventoryPrice = new BigDecimal("0.00");
                for (tbl_treatment_usage e : l_usage) {
                    tbl_inventory inventory = rep_inventory.findById(e.getIdInventory()).get();
                    inventoryPrice = inventoryPrice
                            .add(inventory.getPrice().multiply(new BigDecimal(e.getRealQuantity())));
                }
                model.setInventoryPrice(inventoryPrice);
                model.setTotalPrice(model.getTreatmentPrice().add(model.getInventoryPrice()));
                model.setRealPrice(model.getTotalPrice().subtract(model.getDiscount()));
                if (model.getRealPrice().compareTo(new BigDecimal("0.00")) == -1) {
                    model.setRealPrice(new BigDecimal("0.00"));
                }
                model.setStatus("CALCULATED");
                rep_treatment_tx.save(model);

                resp.setSuccess();
            } else {
                resp.setFail();
                resp.setMessage("HARUS FINISH / CALCULATED DULU");
            }

            // do something
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

    @PostMapping(path = "/tx/complete")
    public ResponseEntity<http_response> completeTX(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            tbl_treatment_tx model = new Gson().fromJson(message, tbl_treatment_tx.class);

            if (model.getStatus().equalsIgnoreCase("CALCULATED")) {
                model.setStatus("COMPLETE");
                rep_treatment_tx.save(model);
                resp.setSuccess();
            } else {
                resp.setFail();
                resp.setMessage("HARUS CALCULATED DULU");
            }

            // do something
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, resp.getStatuscode());

    }

}