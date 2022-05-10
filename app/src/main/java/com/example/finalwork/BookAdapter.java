package com.example.finalwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private int resourceId;
    public BookAdapter(Context context, int textViewResourceId, List<Book>objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public View getView(int position, View converView, ViewGroup parent){
        Book Book = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView BookImage = (ImageView) view.findViewById(R.id.book_image);
        TextView BookName=(TextView) view.findViewById(R.id.book_name);
        TextView Author = (TextView) view.findViewById(R.id.book_author);
        BookName.setText(Book.getName());
        Author.setText(Book.getAuthor());
        Glide.with(getContext()).load(Book.getPhotoUrl()).into(BookImage);
        return view;
    }
}
