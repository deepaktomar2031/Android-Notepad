package com.example.asus.workshop;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity
{
    public static long time;
    DataBaseHelper myDB;
    EditText editText;
    Button btnAdd, btnView, btnShare, btnfeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppRater.app_launched(this);

        editText = (EditText) findViewById(R.id.editTextadd);
        btnAdd = (Button) findViewById(R.id.add);
        btnView = (Button) findViewById(R.id.viewall);
        btnShare = (Button)findViewById(R.id.share);
        btnfeedback = (Button)findViewById(R.id.feedback);
        myDB = new DataBaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0)
                {
                    AddData(newEntry);
                    editText.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "You must put something in the text field", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,ViewListContents.class);
                startActivity(intent);
            }
        });



        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "share our link";
                String shareSub = "https://play.google.com/store/apps/details?id=com.easyalert.lenovo.timetable1";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        btnfeedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"mvdm.csd@gmail.com"});  //developer 's email
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback"); // Email 's Subject
                Email.putExtra(Intent.EXTRA_TEXT, "Dear MVDM Team," + "");  //Email 's Greeting text
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });
    }
    public  void AddData(String newEntry)
    {
        boolean insertData = myDB.addData(newEntry);

        if (insertData==true)
        {
            Toast.makeText(MainActivity.this,"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (time + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            //super.onBackPressed();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Press Once Again To Exit",Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }
}