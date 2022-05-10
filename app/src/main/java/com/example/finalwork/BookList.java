package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity {

    private Intent gotoAddActivity;
    private ListView listView;
    private BookAdapter bookAdapter;
    private List<Book> BookList = new ArrayList<>();
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        SQLiteOpenHelper dbHelper = new SQLiteHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        gotoAddActivity = new Intent(this,AddBook.class);
        Log.v("username",MainActivity.username);
        initBooks();
        listView = (ListView) findViewById(R.id.list_view_book);
        bookAdapter = new BookAdapter(BookList.this,R.layout.book_item,BookList);
        listView.setAdapter(bookAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optin_add:
                startActivity(gotoAddActivity);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initBooks(){
        Cursor cursor1 = db.query("record",null,"Username = ?", new String[]{MainActivity.username},null,null,null);
        while (cursor1.moveToNext()){
            //Log.v("test",String.valueOf(cursor1.getLong(1)));
            Cursor cursor2 = db.query("book",null,"ISBN = ?",new String[]{String.valueOf(cursor1.getLong(1))},null,null,null);
            while (cursor2.moveToNext()){
                BookList.add(new Book(cursor2.getLong(0),cursor2.getString(1),cursor2.getString(2),cursor2.getString(3),cursor2.getString(4),cursor2.getString(5),cursor2.getString(6),cursor2.getString(7),cursor2.getInt(8),cursor2.getInt(9)));
            }
        }
    }
}