package com.skinologi.server.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_inventory_tx {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String idInventoryTx;
    @Column(columnDefinition = "TEXT")
    private String idInventory;

    @Column(columnDefinition = "INT")
    private Integer quantity = 0;

    // add from distributor 1
    // add from bag 2
    // loss to bag 3
    // loss to nothing 4

    // used for treatment from inventory 5
    // used for treatment from bag 6
    @Column(columnDefinition = "INT")
    private Integer type;

    // idpatient
    // idtreatmenttx
    @Column(columnDefinition = "TEXT")
    private String source;

    // idpatient
    // idtreatmenttx
    @Column(columnDefinition = "TEXT")
    private String destination;

    @Column(columnDefinition = "DATE")
    private Date date = new Date();
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String status = "DRAFT";

    @Column(columnDefinition = "TEXT")
    private String createdBy = "SYSTEM";
    @Column(columnDefinition = "DATETIME")
    private Date createdAt = new Date();
    @Column(columnDefinition = "TEXT")
    private String lastModifiedBy;
    @Column(columnDefinition = "DATETIME")
    private Date lastModifiedAt;
    @Column(length = 11)
    private Integer version = 0;

    public tbl_inventory_tx() {
    }

    public tbl_inventory_tx(tbl_inventory_tx model) {
        setIdInventory(model.getIdInventory());
        setQuantity(model.getQuantity());
        setType(model.getType());
        setSource(model.getSource());
        setDestination(model.getDestination());
        // setDate(model.getDate());
        setDescription(model.getDescription());
    }

    public void add_total_minimum(tbl_treatment_usage model, tbl_treatment_tx treatment_tx) {
        setIdInventory(model.getIdInventory());
        setQuantity(model.getTotalQuantity());
        setType(5);
        setSource(model.getIdTreatmentUsage());
        setDestination(treatment_tx.getIdPatient());
        // setDate(model.getDate());
        // setDescription(model.getDescription());
    }

    public void diminish_by_usage(tbl_treatment_usage model, tbl_treatment_tx treatment_tx) {
        setIdInventory(model.getIdInventory());
        setQuantity(model.getUseQuantity());
        setType(6);
        setSource(treatment_tx.getIdPatient());
        setDestination(model.getIdTreatmentUsage());
        // setDate(model.getDate());
        // setDescription(model.getDescription());
    }

    public void edit(tbl_inventory_tx model) {
        setIdInventory(model.getIdInventory());
        setQuantity(model.getQuantity());
        setType(model.getType());
        setSource(model.getSource());
        setDestination(model.getDestination());
        // setDate(model.getDate());
        setDescription(model.getDescription());

        setLastModifiedBy(model.getLastModifiedBy());
        setLastModifiedAt(new Date());
        setVersion(model.getVersion() + 1);
    }
}