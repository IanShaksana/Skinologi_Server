package com.skinologi.server.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter
public class tbl_treatment_usage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String idTreatmentUsage;
    @Column(columnDefinition = "TEXT")
    private String idTreatmentTx;
    @Column(columnDefinition = "TEXT")
    private String idInventory;
    // @Column(columnDefinition = "TEXT")
    // private String idInventoryTx;

    @Column(columnDefinition = "INT")
    private Integer totalQuantity = 0;
    @Column(columnDefinition = "INT")
    private Integer useQuantity = 0;
    @Column(columnDefinition = "INT")
    private Integer realQuantity = 0;

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

    public tbl_treatment_usage() {
    }

}