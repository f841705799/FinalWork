package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.example.finalwork.Book;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity {

    private Intent gotoAddActivity,gotoDetail;
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
        gotoDetail = new Intent(this,BookInfo.class);
        Log.v("username",MainActivity.username);
        initBooks();
        listView = (ListView) findViewById(R.id.list_view_book);
        bookAdapter = new BookAdapter(BookList.this,R.layout.book_item,BookList);
        listView.setAdapter(bookAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoDetail.putExtra("Book", BookList.get(position));
                Toast.makeText(BookList.this,"正在跳转。。。",Toast.LENGTH_SHORT).show();
                startActivity(gotoDetail);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog ad = new AlertDialog.Builder(BookList.this)
                        .setMessage("确认删除？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (db.query("record",null,"ISBN = ?",new String[]{String.valueOf(BookList.get(position).getISBN())},null,null,null).getCount()==1){
                                    db.delete("book","ISBN = ?",new String[]{String.valueOf(BookList.get(position).getISBN())});
                                    db.delete("record","ISBN = ? and Username = ?",new String[]{String.valueOf(BookList.get(position).getISBN()),MainActivity.username});
                                    initBooks();
                                    listView.setAdapter(bookAdapter);
                                }
                                else {
                                    db.delete("record","ISBN = ? and Username = ?",new String[]{String.valueOf(BookList.get(position).getISBN()),MainActivity.username});
                                    initBooks();
                                    listView.setAdapter(bookAdapter);
                                }
                                Toast.makeText(BookList.this,"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create();
                ad.show();
                return true;
            }
        });

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
        BookList.clear();
        Cursor cursor1 = db.query("record",null,"Username = ?", new String[]{MainActivity.username},null,null,null);
        while (cursor1.moveToNext()){
            //Log.v("test",String.valueOf(cursor1.getLong(1)));
            Cursor cursor2 = db.query("book",null,"ISBN = ?",new String[]{String.valueOf(cursor1.getLong(1))},null,null,null);
            while (cursor2.moveToNext()){
                BookList.add(new Book(cursor2.getLong(0),cursor2.getString(1),cursor2.getString(2),cursor2.getString(3),cursor2.getString(4),cursor2.getString(5),cursor2.getString(6),cursor2.getString(7),cursor2.getInt(8),cursor2.getInt(9)));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog ad = new AlertDialog.Builder(BookList.this)
                    .setMessage("确认退出？")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .create();
            ad.show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}