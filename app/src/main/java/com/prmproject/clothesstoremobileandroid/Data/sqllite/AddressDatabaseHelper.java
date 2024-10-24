package com.prmproject.clothesstoremobileandroid.Data.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prmproject.clothesstoremobileandroid.Data.model.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AddressDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "addresses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_COUNTRY = "country";

    public AddressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLNAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_STATE + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_COUNTRY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addAddress(String fullname, String address, String city, String state, String phone, String country) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_STATE, state);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_COUNTRY, country);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }
    public boolean deleteAddress(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("addresses", "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public List<Address> getAllAddresses() {
        List<Address> addressList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int fullNameIndex = cursor.getColumnIndex(COLUMN_FULLNAME);
            int addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);
            int cityIndex = cursor.getColumnIndex(COLUMN_CITY);
            int stateIndex = cursor.getColumnIndex(COLUMN_STATE);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int countryIndex = cursor.getColumnIndex(COLUMN_COUNTRY);

            do {
                Address address = new Address(
                        cursor.getInt(idIndex),
                        cursor.getString(fullNameIndex),
                        cursor.getString(addressIndex),
                        cursor.getString(cityIndex),
                        cursor.getString(stateIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(countryIndex)
                );
                addressList.add(address);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return addressList;
    }


}
