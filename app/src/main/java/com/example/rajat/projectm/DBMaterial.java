package com.example.rajat.projectm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat Dua on 7/22/2016.
 */
public class DBMaterial extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "material.db";
    static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "material";
    public static final String ID = "_id";
    public static final String CATEGORY="category";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    static final String query = "create table " + TABLE_NAME + "("+ID+" integer primary key autoincrement , "+CATEGORY+" text, "+TITLE+" text, "+CONTENT+" text)";

    static SQLiteDatabase db;
    Context context;

    public DBMaterial(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

//    public DBMaterial(FragmentActivity activity, String database, Object o, int i) {
//    super(activity, database, (SQLiteDatabase.CursorFactory) o, i);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insert1(String category,String title, String content) {
        db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(CATEGORY, category);
        newValues.put(TITLE, title);
        newValues.put(CONTENT, content);
        db.insert(TABLE_NAME, null, newValues);
         db.close();
        return true;
    }
    public Cursor getAllData(String category){
        db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select "+TITLE+" from "+TABLE_NAME+" where "+CATEGORY+" = '"+category+"'",null);
        return res;
    }
    public List<String> getAll() {
        List<String> mList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT "+CATEGORY+" FROM " + TABLE_NAME;
        db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                mList.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }
        // return quest list
        db.close();
        return mList;

    }

    public Cursor getContent(String title){
        Cursor x=db.rawQuery("Select "+CONTENT+" from "+TABLE_NAME+" where "+TITLE+" = '"+title+"'",null);
        return x;
    }
    public Cursor getID(String title){
        Cursor x=db.rawQuery("Select "+ID+" from "+TABLE_NAME+" where "+TITLE+" = '"+title+"'",null);
        return x;
    }

    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT "+ID+" FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }

    public Cursor getContentId(int id,String category) {
        Cursor x=db.rawQuery("Select "+TITLE+","+CONTENT+" from "+TABLE_NAME+" where "+ID+" = '"+id+"' and "+CATEGORY+" =  '"+category+"'",null);
        return x;
    }
    public boolean checkID(int id){
        String Query = "Select * from " +TABLE_NAME+ " where " +ID+ " = '"+id+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor getCategory(String i) {
      //  Cursor x=db.rawQuery("Select "+CATEGORY+" from "+TABLE_NAME+" where "+CONTENT+" = '"+i+"'",null);
        Cursor x=db.rawQuery("SELECT "+CATEGORY+" FROM "+TABLE_NAME+" WHERE CONTENT=?",new String[]{i});
        return x;
    }
}
