package com.skinologi.server.table;

import java.time.*;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class tbl_test {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;

    
    @Column(columnDefinition = "DATETIME")
    private Date date1;    
    @Column(columnDefinition = "DATETIME")
    private LocalDate date2;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime date3;
    @Column(columnDefinition = "DATETIME")
    private ZonedDateTime date4;
    // @Column(columnDefinition = "DATETIME")
    // private LocalDate date3;
    
    
}