package com.mvvm.surveyheartcontacts;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public interface DAO {
    String insert(DTO dtoObject, SQLiteDatabase dbObject);
    boolean update(DTO dtoObject, SQLiteDatabase dbObject);
    boolean delete(DTO dtoObject, SQLiteDatabase dbObject);
    ArrayList<DTO> getRecords(SQLiteDatabase dbObject);
}
