package com.example.rajat.projectm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nishant on 29-07-2016.
 */
public class FeedbackDb extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "feedback.db";
    static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME="feedback";
    public static final String ID="_id";
    public static final String NAME="name";
    public static final String FEEDBACK="feedback";
    static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("+ID+" integer primary key autoincrement , "+NAME+" varchar(30),"+FEEDBACK+" text)";
    Context context;
    static SQLiteDatabase db;


    public FeedbackDb(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insert2(String name,String feedback)
    {
        db=this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(NAME, name);
        newValues.put(FEEDBACK, feedback);
        long res=db.insert(TABLE_NAME, null, newValues);
        //db.close();
        if(res==-1){
            return false;
        }
        else {
            return true;
        }
    }
}
