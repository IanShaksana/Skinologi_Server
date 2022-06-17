package com.skinologi.server.table;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter
public class tbl_treatment_tx {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String idTreatmentTx;
    @Column(columnDefinition = "TEXT")
    private String idPatient;

    @Column(columnDefinition = "TEXT")
    private String anamnesis;
    @Column(columnDefinition = "DATE")
    private Date date = new Date();
    @Column(columnDefinition = "LONGTEXT")
    private String status = "DRAFT";

    
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal treatmentPrice = new BigDecimal("0.00");
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal inventoryPrice = new BigDecimal("0.00");    
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal totalPrice = new BigDecimal("0.00");
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal discount = new BigDecimal("0.00");
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal realPrice = new BigDecimal("0.00");

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

    public tbl_treatment_tx() {
    }

    public tbl_treatment_tx(tbl_treatment_tx model) {
        setIdPatient(model.getIdPatient());
        setAnamnesis(model.getAnamnesis());
        setDate(new Date());
    }

    public void edit(tbl_treatment_tx model) {
        setIdPatient(model.getIdPatient());
        setAnamnesis(model.getAnamnesis());
        setDate(model.getDate());

        setLastModifiedBy(model.getLastModifiedBy());
        setLastModifiedAt(new Date());
        setVersion(model.getVersion() + 1);
    }

}