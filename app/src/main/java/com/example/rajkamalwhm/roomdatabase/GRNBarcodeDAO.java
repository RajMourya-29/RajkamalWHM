package com.example.rajkamalwhm.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GRNBarcodeDAO {

    @Query("SELECT * FROM GRNBarcodes")
    List<GRNBarcodes> getAll();

    @Query("SELECT serialno FROM GRNBarcodes")
    List<String> getAllGRNSerialNo();

    @Query("SELECT IFNULL(SUM(qty),0) FROM GRNBarcodes WHERE barcode = :barcode")
    String getGRNSumQty(String barcode);

    @Insert
    void insertgrn(GRNBarcodes barcodes);

    @Query("DELETE FROM GRNBarcodes")
    void deletegrn();

    @Query("SELECT serialno FROM SOBarcodes")
    List<String> getAllSOSerialNo();

    @Query("SELECT IFNULL(SUM(qty),0) FROM SOBarcodes WHERE barcode = :barcode")
    String getSOSumQty(String barcode);

    @Insert
    void insertso(SOBarcodes barcodes);

    @Query("DELETE FROM SOBarcodes")
    void deleteso();

}
