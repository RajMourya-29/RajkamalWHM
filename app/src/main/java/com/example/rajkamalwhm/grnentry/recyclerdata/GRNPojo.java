package com.example.rajkamalwhm.grnentry.recyclerdata;

public class GRNPojo {

    String itemcode,serialno,lotno,qty,piece,uniquecode;

    public GRNPojo(String itemcode,String serialno, String lotno, String qty, String piece, String uniquecode) {
        this.itemcode = itemcode;
        this.serialno = serialno;
        this.lotno = lotno;
        this.qty = qty;
        this.piece = piece;
        this.uniquecode = uniquecode;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    @Override
    public String toString() {
        return "{" +
                "itemcode='" + itemcode + '\'' +
                ", serialno='" + serialno + '\'' +
                ", lotno='" + lotno + '\'' +
                ", qty='" + qty + '\'' +
                ", piece='" + piece + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                '}';
    }

}
