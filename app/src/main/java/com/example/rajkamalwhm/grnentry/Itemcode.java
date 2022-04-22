package com.example.rajkamalwhm.grnentry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemcode {

    @SerializedName("item_code")
    @Expose
    private String itemCode;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}
