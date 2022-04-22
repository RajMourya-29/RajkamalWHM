package com.example.rajkamalwhm.grnentry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastGrnoDetail {
    @SerializedName("grn_id")
    @Expose
    private String grnId;
    @SerializedName("grn_no")
    @Expose
    private String grnNo;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_desc")
    @Expose
    private String itemDesc;

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getGrnNo() {
        return grnNo;
    }

    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

}
