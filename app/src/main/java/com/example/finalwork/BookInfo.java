package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookInfo extends AppCompatActivity {

    private Intent fromList;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        fromList = getIntent();
        Book Book = (com.example.finalwork.Book) getIntent().getSerializableExtra("Book");
        webView = (WebView) findViewById(R.id.Info_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://m.douban.com/book/subject/"+Book.getDouban()+"/");

    }
}