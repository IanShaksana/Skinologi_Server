package com.skinologi.server.model.custom_response;
import com.skinologi.server.table.tbl_inventory;
import com.skinologi.server.table.tbl_patient;
import com.skinologi.server.table.tbl_treatment_tx;
import com.skinologi.server.table.tbl_treatment_usage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class model_treatment__tx_usage_inventory {
    
    tbl_treatment_tx treatmentTx;
    tbl_patient patient;
    tbl_treatment_usage treatmentUsage;
    tbl_inventory inventory;
}
