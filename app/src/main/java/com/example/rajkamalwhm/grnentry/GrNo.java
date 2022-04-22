package com.example.rajkamalwhm.grnentry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrNo {

    @SerializedName("item_srno")
    @Expose
    private String grno;

    public String getGrno() {
        return grno;
    }

    public void setGrno(String grno) {
        this.grno = grno;
    }
}
