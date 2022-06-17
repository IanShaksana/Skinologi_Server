package com.skinologi.server.model.custom_response;

import com.skinologi.server.table.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class model_bag_detail_inventory {

    public model_bag_detail_inventory(tbl_bag_detail bagDetail, tbl_inventory inventory) {
        this.bagDetail = bagDetail;
        this.inventory = inventory;

    }

    tbl_bag_detail bagDetail;
    tbl_inventory inventory;

}
