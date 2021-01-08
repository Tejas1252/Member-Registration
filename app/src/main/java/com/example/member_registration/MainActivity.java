package com.example.member_registration;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String TAG ="MainActivity";
    private DatePickerDialog.OnDateSetListener dateSet1,dateSet2;
    EditText name,email,address,contact,dob,doa,city;
    ImageView imageView;
    Button save;
    FloatingActionButton search;
    Data db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS},0);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter cspinner=ArrayAdapter.createFromResource(this,R.array.spinnerItems,R.layout.csl);
        cspinner.setDropDownViewResource(R.layout.dsl);
        spinner.setAdapter(cspinner);
        spinner.setOnItemSelectedListener(this);

        db=new Data(this);
        name=(EditText)findViewById(R.id.editText1);
        email=(EditText)findViewById(R.id.editText2);
        address=(EditText)findViewById(R.id.editText3);
        contact=(EditText)findViewById(R.id.editText4);
        dob=(EditText)findViewById(R.id.editText5);
        doa=(EditText)findViewById(R.id.editText6);
        city=(EditText)findViewById(R.id.editText8);
        save=(Button)findViewById(R.id.button);
        search=(FloatingActionButton)findViewById(R.id.button2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(name.getText().toString().equals("") || email.getText().toString().equals("") || address.getText().toString().equals("") || contact.getText().toString().equals("") || dob.getText().toString().equals("") || doa.getText().toString().equals("")  || spinner.getSelectedItem().equals("None")|| city.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter entire data.", Toast.LENGTH_SHORT).show();

                }
                    else {
                   String name1 = name.getText().toString().trim();
                   String email1 = email.getText().toString().trim();
                   String address1 = address.getText().toString().trim();
                   String contact1 = contact.getText().toString().trim();
                   String dob1 = dob.getText().toString().trim();
                   String doa1 = doa.getText().toString().trim();
                   String state1 = String.valueOf(spinner.getSelectedItem());
                   String city1 = city.getText().toString().trim();
                   boolean res = db.checkUser(email1, contact1);
                   if (res) {
                       boolean res2 = db.checkDate(dob1, doa1);
                       if (res2) {
                           db.addUser(name1, email1, address1, contact1, dob1, doa1, state1, city1);
//                           name.setText("");
//                           email.setText("");
//                           address.setText("");
//                           contact.setText("");
//                           dob.setText("");
//                           doa.setText("");
//                           spinner.setSelection(0);
//                           city.setText("");
                           Toast.makeText(MainActivity.this, "Member added successfully!!", Toast.LENGTH_SHORT).show();
                           String c=contact.getText().toString();
                           String n=name.getText().toString();
                           Intent intent=new Intent(MainActivity.this, ImagePicker.class);
                           intent.putExtra("c",c);
                           intent.putExtra("n",n);
                           startActivity(intent);
                       } else {
                           Toast.makeText(MainActivity.this, "D.O.A. should be greater than D.O.B.", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Toast.makeText(MainActivity.this, "Either E-mail or Contact already exists!!", Toast.LENGTH_LONG).show();

                   }
               }

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSet1,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year1=calendar.get(Calendar.YEAR);
                int month1=calendar.get(Calendar.MONTH);
                int day1=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSet2,year1,month1,day1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSet1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                dob.setText(date);
            }
        };
        dateSet2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date1=day+"/"+month+"/"+year;
                doa.setText(date1);
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
     }
    public void onNothingSelected(AdapterView<?> arg0) {
     }
}
