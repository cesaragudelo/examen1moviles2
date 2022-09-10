package com.example.viajeexamen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClsOpenHelper extends SQLiteOpenHelper {
    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table TblViaje(codViaje text primary key , ciudadDestino text not null ," +
                " cantPersonas text not null, valorPersona integer not null, activo text not null default 'si')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE TblViaje");
        {
            onCreate(sqLiteDatabase);
        }
    }
}