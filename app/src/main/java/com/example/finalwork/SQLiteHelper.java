package com.example.finalwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="bookdatabase";
    private static final  int DB_VERSION = 1;
    private Context context;

    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE user(Username TEXT PRIMARY KEY,Password TEXT);");
            db.execSQL("CREATE TABLE book(ISBN INTERGER PRIMARY KEY,name TEXT,author TEXT,authorIntro TEXT,photoUrl TEXT,publishing TEXT,published TEXT,description TEXT,douban Integer,doubanScore INTERGER);");
            db.execSQL("CREATE TABLE record(Username TEXT,ISBN Integer);");
        }


    }
    public static void insertUser(SQLiteDatabase db,String Username,String Password){
        ContentValues UserValues = new ContentValues();
        UserValues.put("Username",Username);
        UserValues.put("Password",Password);
        db.insert("user",null,UserValues);
    }
    public static void insertBook(SQLiteDatabase db,Long ISBN,String name,String author,String authorIntro,String photoUrl,String publishing ,String published,String description,Integer douban,Integer doubanScore){
        ContentValues BookValues = new ContentValues();
        BookValues.put("ISBN",ISBN);
        BookValues.put("name",name);
        BookValues.put("author",author);
        BookValues.put("authorIntro",authorIntro);
        BookValues.put("photoUrl",photoUrl);
        BookValues.put("publishing",publishing);
        BookValues.put("published",published);
        BookValues.put("description",description);
        BookValues.put("douban",douban);
        BookValues.put("doubanScore",doubanScore);
        db.insert("book",null,BookValues);
    }
    public static void insertBookRecord(SQLiteDatabase db,String Username,Long ISBN){
        ContentValues RecordValues = new ContentValues();
        RecordValues.put("Username",Username);
        RecordValues.put("ISBN",ISBN);
        db.insert("record",null,RecordValues);
    }
}