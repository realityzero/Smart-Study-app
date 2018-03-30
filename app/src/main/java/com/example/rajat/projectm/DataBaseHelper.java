package com.example.rajat.projectm;

/**
 * Created by Rajat Dua on 7/11/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class DataBaseHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME="user";
    public static final String ID="_id";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String TYPE="type";
    //public static final String x="NOT EXIST";


    static final String DATABASE_CREATE = "create table "+TABLE_NAME+
            " ( " +ID+" integer primary key ,"+NAME+" varchar(30),"+EMAIL+" varchar(30),"+PASSWORD+" varchar(30),"+TYPE+" varchar(30)) ";
   // private static android.database.sqlite.SQLiteDatabase db;
    Context context;
    static SQLiteDatabase db;

    //public SQLiteDatabase db;

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
//        db=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE);
        Log.d("msg","ex");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
// Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

     public boolean insertData(String name,String email,String password,String type)
    {
        db=this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(NAME, name);
        newValues.put(EMAIL, email);
        newValues.put(PASSWORD,password);
        newValues.put(TYPE,type);
        long result=db.insert(TABLE_NAME, null, newValues);


        if(result==-1){
            return false;
        }
        else{
            return true;
        }
///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
//    static public String getSingleEntry(String email)
//    {           //Cursor cursor=db.query(TABLE_NAME, null, "EMAIL=?", new String[]{email}, null, null, null);
//        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME+" where EMAIL=?",null);
//        if(cursor.getCount()<1) // UserName Not Exist
//        {
//            cursor.close();
//            return x;
//        }
//
//            cursor.moveToFirst();
//            String password = cursor.getString(cursor.getColumnIndex(col_4));
//            cursor.close();
//            db.close();
//            return password;
//
    //}
//    public static String getSingleEntry(String email){
//    Cursor cursor=null;
//    String password = "";
//    //SQLiteDatabase db1 = null;
//    //db=this.getReadableDatabase();
//    try{
//        cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE email=?",new String[]{email});
//
//        if(cursor.getCount()>0) {
//            cursor.moveToFirst();
//            password = cursor.getString(cursor.getColumnIndex(col_4));
//        }
//        return password;
//    }finally {
//        cursor.close();
//
//    }

//}

public  Cursor getSingleEntry(String n, String p){
    String cols[]={EMAIL,PASSWORD};
    String args[]={n,p};
//    Cursor cur=db.query(TABLE_NAME,cols,EMAIL+"=?"+"and"+PASSWORD+"=?",args,null,null,null);
            db = this.getReadableDatabase();
    //Cursor  cur= db.rawQuery("select * from "+ TABLE_NAME+" where "+EMAIL+"='?' and "+ PASSWORD+"=?",new String[]{n,p});
Cursor cur=db.rawQuery("Select * from "+TABLE_NAME+" where "+EMAIL+" = '"+n+"' AND "+PASSWORD+" = '"+p+"'",null);

        return cur;
}
    public Cursor getAllData(String email){
        db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select "+TYPE+" from "+TABLE_NAME+" where "+EMAIL+" = '"+email+"'",null);


        return res;
    }

}