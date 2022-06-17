package com.skinologi.server.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_bag {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length =50)
    private String idBag;
    @Column(columnDefinition = "TEXT")
    private String idPatient;

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
    
}