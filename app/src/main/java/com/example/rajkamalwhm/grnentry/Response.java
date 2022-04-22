package com.example.rajkamalwhm.grnentry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("PoNo")
    @Expose
    private List<PoNo> poNo = null;

    @SerializedName("Itemcode")
    @Expose
    private List<Itemcode> itemcode = null;

    @SerializedName("Grno")
    @Expose
    private List<GrNo> grNoList = null;

    @SerializedName("LastGrnoDetails")
    @Expose
    private List<LastGrnoDetail> lastGrnoDetails = null;

    public String getResponse() {
        return response;
    }

    public List<PoNo> getPoNo() {
        return poNo;
    }

    public List<Itemcode> getItemcode() {
        return itemcode;
    }

    public List<GrNo> getgrno() {
        return grNoList;
    }

    public List<LastGrnoDetail> getLastGrnoDetails() {
        return lastGrnoDetails;
    }

}