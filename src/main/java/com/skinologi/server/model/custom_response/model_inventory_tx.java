package com.skinologi.server.model.custom_response;

import com.skinologi.server.table.tbl_inventory;
import com.skinologi.server.table.tbl_inventory_tx;
import com.skinologi.server.table.tbl_patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class model_inventory_tx {

    public model_inventory_tx(tbl_inventory inventory, tbl_inventory_tx inventoryTx) {
        this.inventory = inventory;
        this.inventoryTx = inventoryTx;
    }

    public model_inventory_tx(tbl_inventory inventory, tbl_inventory_tx inventoryTx, tbl_patient patient) {
        this.inventory = inventory;
        this.inventoryTx = inventoryTx;
        this.patient = patient;
    }

    tbl_inventory inventory;
    tbl_inventory_tx inventoryTx;
    tbl_patient patient;

}
