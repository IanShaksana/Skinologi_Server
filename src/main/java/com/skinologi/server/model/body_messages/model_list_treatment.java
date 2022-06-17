package com.skinologi.server.model.body_messages;

import java.util.List;

import com.skinologi.server.table.tbl_inventory_tx;
import com.skinologi.server.table.tbl_treatment;
import com.skinologi.server.table.tbl_treatment_tx;

import lombok.Getter;

@Getter
public class model_list_treatment {

    tbl_treatment_tx treatmentTx;
    List<tbl_treatment> treatment;    
    List<tbl_inventory_tx> inventoryTx;

}
