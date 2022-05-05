package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BookList extends AppCompatActivity {

    private Intent fromMain,gotoAddActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        fromMain = getIntent();
        String username = fromMain.getStringExtra("username");
        gotoAddActivity = new Intent(this,AddBook.class);
        Log.v("username",username);
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
}