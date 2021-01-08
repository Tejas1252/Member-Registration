package com.example.member_registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.theartofdev.edmodo.cropper.CropImage;

public class ImagePicker extends AppCompatActivity implements View.OnClickListener{
    private static final int CAMERA_REQUEST = 1888;
    SmsManager sms=SmsManager.getDefault();
    ImageView imageView;
    Button b1;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        imageView=(ImageView)findViewById(R.id.iv2);
        b1=(Button)findViewById(R.id.pick2);
        b1.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(ImagePicker.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(ImagePicker.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            imageView.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Bundle extras=getIntent().getExtras();
                String contact=extras.getString("c");
                Data data1=new Data(this);
                uri=result.getUri();
                String x=getPath(uri);
                if (data1.insertImage(x,contact))
            {
                imageView.setTag("Done");
            }
            else {
                Toast.makeText(getApplicationContext(),"Error!!",Toast.LENGTH_SHORT).show();
            }
                imageView.setImageURI(uri);
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
            case R.id.pick2:
                CropImage.activity().start(ImagePicker.this);
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.done:
                if ("notdone".equals(imageView.getTag())){
                    Toast.makeText(getApplicationContext(),"Please pick image!!",Toast.LENGTH_SHORT).show();
                    }
                else{
                    Bundle extras=getIntent().getExtras();
                    String contact=extras.getString("c");
                    String name=extras.getString("n");
                    Toast.makeText(getApplicationContext(),"Image Inserted!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ImagePicker.this,MainActivity.class));
                    sms.sendTextMessage(contact,null,"Dear "+name+","+"\nRegistration Successfull",null,null);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
