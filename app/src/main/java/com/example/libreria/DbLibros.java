package com.example.libreria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLibros extends SQLiteOpenHelper {
    String tblLibro = "Create Table libro(etId text primary key, " +
            "nombre text, costo txt, dispo txt)";
    //falta una tabla
    public DbLibros( Context context, String nombre,SQLiteDatabase.CursorFactory factory, int version ){
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblLibro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table Libro");
            db.execSQL(tblLibro);
    }
}
