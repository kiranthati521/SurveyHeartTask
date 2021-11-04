package com.mvvm.surveyheartcontacts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.mvvm.surveyheartcontacts.dbhandler.DBHandler;
import java.util.ArrayList;

public class ContactsListDAO implements DAO {
    private final String TAG = "contactsListDAO";
    private static ContactsListDAO contactsListDAO;

    public static ContactsListDAO getInstance() {
        if (contactsListDAO == null) {
            contactsListDAO = new ContactsListDAO();
        }
        return contactsListDAO;
    }

    @Override
    public String insert(DTO dtoObject, SQLiteDatabase dbObject) {
        try {
            ContactModel dto = (ContactModel) dtoObject;
            ContentValues cv = new ContentValues();

            cv.put("name", dto.getName());
            cv.put("mobileNumber", dto.getMobileNumber());
            cv.put("email", dto.getEmail());
            cv.put("contactType", dto.getContactType());


            long rowsEffected = dbObject.insert(DBHandler.TABLE_CONTACTS, null, cv);
            if (rowsEffected > 0)
                return "";

        } catch (SQLException e) {
            return "";
        } finally {
            dbObject.close();
        }

        return null;
    }

    @Override
    public boolean update(DTO dtoObject, SQLiteDatabase dbObject) {

        try {
            ContactModel dto = (ContactModel) dtoObject;
            ContentValues cv = new ContentValues();
            cv.put("name", dto.getName());
            cv.put("mobileNumber", dto.getMobileNumber());
            cv.put("email", dto.getEmail());
            cv.put("contactType", dto.getContactType());


            dbObject.update(DBHandler.TABLE_CONTACTS, cv, "id = '"+dto.getId()+"'", null);
            return true;

        } catch (SQLException e) {

        } finally {
            dbObject.close();
        }

        return false;
    }

    @Override
    public boolean delete(DTO dtoObject, SQLiteDatabase dbObject) {
        return false;
    }

    @Override
    public ArrayList<DTO> getRecords(SQLiteDatabase dbObject) {
        ArrayList<DTO> contactDto = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dbObject.rawQuery("SELECT * FROM " + DBHandler.TABLE_CONTACTS, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ContactModel dto = new ContactModel();
                    dto.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    dto.setName(cursor.getString(cursor.getColumnIndex("name")));
                    dto.setMobileNumber(cursor.getString(cursor.getColumnIndex("mobileNumber")));
                    dto.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    dto.setContactType(cursor.getString(cursor.getColumnIndex("contactType")));

                    contactDto.add(dto);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            dbObject.close();
        }

        return contactDto;
    }

    public ArrayList<ContactModel> getRecordsList(SQLiteDatabase dbObject) {
        ArrayList<ContactModel> contactDto = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dbObject.rawQuery("SELECT * FROM " + DBHandler.TABLE_CONTACTS, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ContactModel dto = new ContactModel();
                    dto.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    dto.setName(cursor.getString(cursor.getColumnIndex("name")));
                    dto.setMobileNumber(cursor.getString(cursor.getColumnIndex("mobileNumber")));
                    dto.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    dto.setContactType(cursor.getString(cursor.getColumnIndex("contactType")));

                    contactDto.add(dto);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            dbObject.close();
        }

        return contactDto;
    }



    public boolean deleteTableData(SQLiteDatabase dbObject) {
        try {
            dbObject.compileStatement("DELETE FROM " + DBHandler.TABLE_CONTACTS).execute();
            return true;
        } catch (Exception e) {

        } finally {
            dbObject.close();
        }
        return false;
    }

    public boolean deleteRecordById(long id, SQLiteDatabase dbObject) {
        try {
            int rowsEffected = dbObject.delete(DBHandler.TABLE_CONTACTS, "id = '"+id+"'", null);
            return rowsEffected > 0;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            dbObject.close();
        }
        return false;
    }

    public int isDataAvailable(SQLiteDatabase dbObject) {
        Cursor cursor = null;
        try {
            cursor = dbObject.rawQuery("SELECT count(id) FROM "+ DBHandler.TABLE_CONTACTS, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return 0;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            dbObject.close();
        }
        return 0;
    }
}
