package com.skinologi.server.model.custom_response;

import com.skinologi.server.table.tbl_appointment;
import com.skinologi.server.table.tbl_patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_appointment_patient {

    public model_appointment_patient(tbl_appointment appointment, tbl_patient patient) {
        this.appointment = appointment;
        this.patient = patient;
    }

    tbl_appointment appointment;
    tbl_patient patient;
}
