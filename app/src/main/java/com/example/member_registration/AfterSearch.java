package com.example.member_registration;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Calendar;

public class AfterSearch extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog.OnDateSetListener dateSet1,dateSet2;
    Button upd,dlt,change;
    ImageView updateImageView;
    EditText editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9;
    TextView editText1;
    Data data;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extra=getIntent().getExtras();
        String string=extra.getString("searchbar");
        data=new Data(this);
        updateImageView=(ImageView)findViewById(R.id.uiv);
        upd=(Button)findViewById(R.id.update2);
        dlt=(Button)findViewById(R.id.delete2);
        change=(Button)findViewById(R.id.change);
        change.setOnClickListener(this);
        editText1=(TextView) findViewById(R.id.id3);
        editText2=(EditText)findViewById(R.id.name3);
        editText3=(EditText)findViewById(R.id.email3);
        editText4=(EditText)findViewById(R.id.address3);
        editText5=(EditText)findViewById(R.id.contact3);
        editText6=(EditText)findViewById(R.id.DOB3);
        editText7=(EditText)findViewById(R.id.DOA3);
        editText8=(EditText)findViewById(R.id.state3);
        editText9=(EditText)findViewById(R.id.city3);

            Cursor cursor = data.getAll(string);
            cursor.moveToFirst();
            editText1.setText(cursor.getString(0));
            editText2.setText(cursor.getString(1));
            editText3.setText(cursor.getString(2));
            editText4.setText(cursor.getString(3));
            editText5.setText(cursor.getString(4));
            editText6.setText(cursor.getString(5));
            editText7.setText(cursor.getString(6));
            editText8.setText(cursor.getString(7));
            editText9.setText(cursor.getString(8));
//            updateImageView.setImageResource(R.drawable.sample);
            updateImageView.setImageBitmap(data.getImage(string));
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd=editText1.getText().toString();
                String namee= String.valueOf(editText2.getText());
                String emaill= String.valueOf(editText3.getText());
                String addresss= String.valueOf(editText4.getText());
                String contactt= String.valueOf(editText5.getText());
                String DOBB= String.valueOf(editText6.getText());
                String DOAA= String.valueOf(editText7.getText());
                String statee=String.valueOf(editText8.getText());
                String cityy= String.valueOf(editText9.getText());
                boolean res=data.updateUser(idd,namee,emaill,addresss,contactt,DOBB,DOAA,statee,cityy);
                if (res)
                {
                    //insertImage
                    Toast.makeText(getApplicationContext(),"Updated Successfully!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Updation Error!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        editText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AfterSearch.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSet1,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSet1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                editText6.setText(date);
            }
        };
        editText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AfterSearch.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSet2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSet2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                editText7.setText(date);
            }
        };
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd=editText1.getText().toString();
                boolean res=data.deleteUser(idd);
                if (res)
                {
                    Toast.makeText(getApplicationContext(),"Deleted Successfully!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid ID!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                String contact=editText5.getText().toString();
                Data data1=new Data(this);
                uri=result.getUri();
                String x=getPath(uri);
                if (data1.insertImage(x,contact))
                {
                    Toast.makeText(getApplicationContext(),"Image Changed!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error!!",Toast.LENGTH_SHORT).show();
                }
                updateImageView.setImageURI(uri);
            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e=result.getError();
                Toast.makeText(getApplicationContext(),"The error is : "+e,Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getPath(Uri uri) {
        if (uri==null)return null;
        String[] projection={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(uri,projection,null,null,null);
        if (cursor!=null){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                CropImage.activity().start(AfterSearch.this);
                break;
        }
    }
}
