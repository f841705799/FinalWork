package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BookList extends AppCompatActivity {

    private Intent fromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        fromMain = getIntent();
        String username = fromMain.getStringExtra("username");
        Log.v("username",username);
    }
}