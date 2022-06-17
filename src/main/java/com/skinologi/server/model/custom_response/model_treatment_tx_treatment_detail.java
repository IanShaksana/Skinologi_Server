package com.skinologi.server.model.custom_response;

import java.util.List;

import com.skinologi.server.table.tbl_inventory_tx;
import com.skinologi.server.table.tbl_patient;
import com.skinologi.server.table.tbl_treatment;
import com.skinologi.server.table.tbl_treatment_detail;
import com.skinologi.server.table.tbl_treatment_tx;
import com.skinologi.server.table.tbl_treatment_usage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class model_treatment_tx_treatment_detail {
    
    tbl_treatment_tx treatmentTx;
    tbl_patient patient;
    List<tbl_treatment> treatment;
    List<tbl_treatment_detail> treatmentDetail;
    List<tbl_treatment_usage> treatmentUsage;
    List<tbl_inventory_tx> inventoryTx;
}
