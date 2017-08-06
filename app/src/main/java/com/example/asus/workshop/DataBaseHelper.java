package com.example.asus.workshop;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper
{

    public  static final String DATABASE_NAME = "mylist.db";
    public  static final String TABLE_NAME = "mylist_data";
    public  static final String COL_1 = "ID";
    public  static final String COL_2 = "ITEM1";

    public DataBaseHelper(Context context){super (context, DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM1 TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    }


    public boolean addData (String item1)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item1);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getListContents()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME,null);
        return data;
    }
}