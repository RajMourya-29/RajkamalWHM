package com.example.rajkamalwhm.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class GRNBarcodes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "serialno")
    private String serialno;

    @ColumnInfo(name = "lotno")
    private String lotno;

    @ColumnInfo(name = "qty")
    private String qty;

    @ColumnInfo(name = "piece")
    private String piece;

    @ColumnInfo(name = "uniquecode")
    private String uniquecode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
        return barcode    + "," +
                serialno    + "," +
                lotno  + "," +
                qty  + "," +
                piece   + "" +
                uniquecode + System.lineSeparator() ;
    }

}
