package com.skinologi.server.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_appointment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length =50)
    private String idAppointment;
    @Column(columnDefinition = "TEXT")
    private String idPatient;
    @Column(columnDefinition = "DATETIME")
    private Date date;
    @Column(columnDefinition = "TIME")
    private Date time;
    @Column(columnDefinition = "LONGTEXT")
    private String description;


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

    public tbl_appointment(){
        
    }

    public tbl_appointment(tbl_appointment model) {
        setIdPatient(model.getIdPatient());
        setDate(model.getDate());
        setTime(model.getTime());
        setDescription(model.getDescription());
    }

    public void edit(tbl_appointment model) {
        setIdPatient(model.getIdPatient());
        setDate(model.getDate());
        setTime(model.getTime());
        setDescription(model.getDescription());

        setLastModifiedBy(model.getLastModifiedBy());
        setLastModifiedAt(new Date());
        setVersion(model.getVersion() + 1);
    }
}