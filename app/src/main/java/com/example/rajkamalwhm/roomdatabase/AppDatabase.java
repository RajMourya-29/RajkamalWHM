package com.example.rajkamalwhm.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GRNBarcodes.class,SOBarcodes.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GRNBarcodeDAO grnbarcodeDAO();
}