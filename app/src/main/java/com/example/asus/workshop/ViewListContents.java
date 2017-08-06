package com.example.asus.workshop;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity
{
    DataBaseHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new DataBaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if (data.getCount() ==0)
        {
            Toast.makeText(ViewListContents.this,"The Database was Empty",Toast.LENGTH_LONG).show();
        }
        else {
            while (data.moveToNext())
            {
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
    }
}