package com.skinologi.server.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_patient {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String idPatient;

    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "DATE")
    private Date dob;
    @Column(columnDefinition = "TEXT")
    private String email;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column(columnDefinition = "TEXT")
    private String contact;
    @Column(columnDefinition = "TEXT")
    private String blood;

    @Column(columnDefinition = "TEXT")
    private String status = "ACTIVE";
    @Column(columnDefinition = "TEXT")
    private String rank = "MEMBER";

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

    public tbl_patient() {

    }

    public tbl_patient(tbl_patient model) {
        setName(model.getName());
        setDob(model.getDob());
        setEmail(model.getEmail());
        setAddress(model.getAddress());
        setContact(model.getContact());
        setBlood(model.getBlood());
    }

    public void edit(tbl_patient model) {
        setName(model.getName());
        setDob(model.getDob());
        setEmail(model.getEmail());
        setAddress(model.getAddress());
        setContact(model.getContact());
        setBlood(model.getBlood());

        setLastModifiedBy(model.getLastModifiedBy());
        setLastModifiedAt(new Date());
        setVersion(model.getVersion() + 1);
    }

}