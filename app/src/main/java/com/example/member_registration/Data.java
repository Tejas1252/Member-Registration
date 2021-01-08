package com.example.member_registration;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.annotation.Nullable;
import com.example.member_registration.Model.Emp;

public class Data extends SQLiteOpenHelper {
    private static final String Database_name = "Employee.db";
    public static final String Table_name = "Employee";
    public static final String Col = "Id";
    public static final String Col1 = "Name";
    public static final String Col2 = "E_mail";
    public static final String Col3 = "Address";
    public static final String Col4 = "Contact";
    public static final String Col5 = "DOB";
    public static final String Col6 = "DOA";
    public static final String Col7 = "State";
    public static final String Col8 = "City";
    public static final String Col9 = "Image";


    public Data(@Nullable Context context) {
        super(context, Database_name, null, 1);
        SQLiteDatabase db=getWritableDatabase();
        ContentResolver contentResolver=context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Employee(Id integer primary key autoincrement, Name text not null,E_mail text not null,Address text not null,Contact integer not null,DOB text not null,DOA text not null,State text not null,City text not null,Image blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table_name);
        onCreate(db);

    }

    public long addUser(String Name1, String E_mail1, String Address1, String Contact1, String DOB1, String DOA1, String State1, String City1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name1);
        contentValues.put("E_mail", E_mail1);
        contentValues.put("Address", Address1);
        contentValues.put("Contact", Contact1);
        contentValues.put("DOB", DOB1);
        contentValues.put("DOA", DOA1);
        contentValues.put("State", State1);
        contentValues.put("City", City1);
        long res = db.insert("Employee", null, contentValues);
        db.close();
        return res;

    }
    public void addToDb(byte[] image){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col9,image);
        db.insert(Table_name,null,contentValues);
    }
    public boolean insertImage(String x,String contact){
        SQLiteDatabase db=getWritableDatabase();
        try{
            FileInputStream fs=new FileInputStream(x);
            byte[] imgByte= new byte[fs.available()];
            fs.read(imgByte);
            ContentValues cv=new ContentValues();
            cv.put("Image",imgByte);
            db.update(Table_name,cv,"Contact=?",new String[]{String.valueOf(contact)});
            fs.close();
            return true;
        }
        catch(IOException e){e.printStackTrace();
        return false;
        }
    }
    public Bitmap getImage(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        Bitmap bt=null;
        Cursor cursor1=db.rawQuery("Select * from Employee where Name=?",new String[]{String.valueOf(name)});
        if (cursor1.moveToNext()){
            byte[] img=cursor1.getBlob(9);
            bt= BitmapFactory.decodeByteArray(img,0,img.length);
            cursor1.close();
        }
        return bt;
    }
    public boolean updateUser(String Id1, String Name1, String E_mail1, String Address1, String Contact1, String DOB1, String DOA1, String State1, String City1){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Id",Id1);
        contentValues.put("Name",Name1);
        contentValues.put("E_mail",E_mail1);
        contentValues.put("Address",Address1);
        contentValues.put("Contact",Contact1);
        contentValues.put("DOB",DOB1);
        contentValues.put("DOA",DOA1);
        contentValues.put("State",State1);
        contentValues.put("City",City1);
        db.update("Employee",contentValues,"Id=?",new String[]{String.valueOf(Id1)});
        Cursor cursor=db.rawQuery("Select Name from Employee where Id=?",new String[]{Id1});
        int count=cursor.getCount();
        if (count>0) {return true;}
        else {return  false;}
    }
public boolean checkSearch(String search){
        SQLiteDatabase db=getReadableDatabase();
    Cursor cursor=db.rawQuery("Select Id from Employee where Name=?",new String[]{search});
    int count=cursor.getCount();
    if (count>0) {return true;}
    else {return  false;}
}
public boolean deleteUser(String id){
        SQLiteDatabase db=getWritableDatabase();
        String[] columns={Col1};
        String selection=Col+"=?";
        String[] selectArgs={id};
        //select Name from Employee where Id=id;
        Cursor cursor=db.query(Table_name,columns,selection,selectArgs,null,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            db.execSQL("delete from "+ Table_name+ " where "+"Id"+"="+id);
            db.close();
            return true;
        } else {
            return false;
        }
}
public boolean checkUpdateId(int id){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("Select Name from Employee where Id=?",new String[]{String.valueOf(id)});
        int count=cursor.getCount();
        if (count>0) {
            return true;
        }
        else {
            return  false;
        }

}
public Cursor getAll(String i){
        SQLiteDatabase db=getWritableDatabase();
//        String query="Select * from Employee where Name="+i;
        Cursor cursor=db.rawQuery("Select Id,Name,E_mail,Address,Contact,DOB,DOA,State,City from Employee where Name=?",new String[]{String.valueOf(i)});
        return cursor;
}
    public boolean checkUser(String email, String contact) {
        String[] columns = {Col};
        SQLiteDatabase db = getReadableDatabase();
        String selection = Col2 + "=?" + " or " + Col4 + "=?";
        String[] selectionArgs = {email, contact};
        //select Id from Employee where E_mail=email or Contact=contact;
        Cursor cursor = db.query(Table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkDate(String dob, String doa) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date d1 = sdf.parse(dob);
            Date d2 = sdf.parse(doa);
            if (d1.compareTo(d2) > 0) {
                return false;
            }
            if (d1.compareTo(d2) == 0) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;

    }

    public List<Emp> getEmp() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Id", "Name", "E_mail", "Address", "Contact", "DOB", "DOA", "State", "City"};
        String tableName = "Employee";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<Emp> result = new ArrayList<>();
        if (cursor!=null && cursor.getCount()>0) {
            if (cursor.moveToFirst())
            {
                do {
                    Emp emp = new Emp();
                    emp.setId(cursor.getString(cursor.getColumnIndex("Id")));
                    emp.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    emp.setE_mail(cursor.getString(cursor.getColumnIndex("E_mail")));
                    emp.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                    emp.setContact(cursor.getString(cursor.getColumnIndex("Contact")));
                    emp.setDOB(cursor.getString(cursor.getColumnIndex("DOB")));
                    emp.setDOA(cursor.getString(cursor.getColumnIndex("DOA")));
                    emp.setState(cursor.getString(cursor.getColumnIndex("State")));
                    emp.setCity(cursor.getString(cursor.getColumnIndex("City")));
                    result.add(emp);
                } while (cursor.moveToNext());
            }
        }
        return result;
    }

    public List<String> getNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name"};
        String tableName = "Employee";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor!=null && cursor.getCount()>0) {
            if (cursor.moveToFirst()){
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            } while (cursor.moveToNext());
        }
        }
        return result;
    }

    public List<Emp> getEmpByName(String Name)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name"};
        String tableName = "Employee";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, "Name like ?", new String[]{"%"+Name+"%"}, null, null, null);
        List<Emp> result = new ArrayList<>();
        if (cursor!=null && cursor.getCount()>0) {
            if (cursor.moveToFirst())
            {
                do {
                    Emp emp = new Emp();
                    emp.setId(cursor.getString(cursor.getColumnIndex("Id")));
                    emp.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    emp.setE_mail(cursor.getString(cursor.getColumnIndex("E_mail")));
                    emp.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                    emp.setContact(cursor.getString(cursor.getColumnIndex("Contact")));
                    emp.setDOB(cursor.getString(cursor.getColumnIndex("DOB")));
                    emp.setDOA(cursor.getString(cursor.getColumnIndex("DOA")));
                    emp.setState(cursor.getString(cursor.getColumnIndex("State")));
                    emp.setCity(cursor.getString(cursor.getColumnIndex("City")));
                    result.add(emp);
                } while (cursor.moveToNext());
            }
        }
        return result;
    }

}
