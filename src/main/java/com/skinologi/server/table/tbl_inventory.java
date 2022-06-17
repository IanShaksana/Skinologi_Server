package com.skinologi.server.table;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_inventory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String idInventory;

    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String unit;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(columnDefinition = "INT")
    private Integer quantity = 0;
    @Column(columnDefinition = "DECIMAL")
    private BigDecimal price = new BigDecimal("0.00");
    @Column(columnDefinition = "TEXT")
    private String status = "ACTIVE";

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

    public tbl_inventory() {
    }

    public tbl_inventory(tbl_inventory model) {
        setName(model.getName());
        setUnit(model.getUnit());
        setPrice(model.getPrice());
        setDescription(model.getDescription());
        setQuantity(model.getQuantity());
        
    }

    public void edit(tbl_inventory model) {
        setName(model.getName());
        setUnit(model.getUnit());
        setPrice(model.getPrice());
        setDescription(model.getDescription());

        setLastModifiedBy(model.getLastModifiedBy());
        setLastModifiedAt(new Date());
        setVersion(model.getVersion() + 1);
    }
}