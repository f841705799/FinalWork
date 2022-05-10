package com.example.finalwork;

import static com.example.finalwork.SQLiteHelper.insertUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button loginB,registerB;
    private EditText usernameE,passwordE;
    private CheckBox rememberC;
    private Intent gotoListActivity;
    private String password;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteOpenHelper dbHelper = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        registerB = (Button) findViewById(R.id.Button_reg);
        loginB = (Button) findViewById(R.id.Button_log);
        usernameE = (EditText) findViewById(R.id.Edit_username);
        passwordE = (EditText) findViewById(R.id.Edit_password);
        rememberC = (CheckBox) findViewById(R.id.Check_remember);
        SharedPreferences spf = getSharedPreferences("account",MODE_PRIVATE);
        username = spf.getString("username","");
        password = spf.getString("password","");
        gotoListActivity = new Intent(this,BookList.class);
        if ( username != "" & password != ""){
            usernameE.setText(username);
            passwordE.setText(password);
            rememberC.setChecked(true);
        }
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameE.getText().toString();
                password = passwordE.getText().toString();
                if (username.equals("") || password.equals("")){
                    Toast.makeText(MainActivity.this,"用户名或密码不可为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (db.query("user", null,"username = ?", new String[]{username},null,null,null).getCount() == 0){
                        insertUser(db,username,password);
                        Toast.makeText(MainActivity.this,"注册成功，点击登录进入",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"注册失败，用户名已存在",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录流程
                username = usernameE.getText().toString();
                password = passwordE.getText().toString();
                Cursor cursor = db.query("user", null,"username = ?", new String[]{username},null,null,null);
                cursor.moveToFirst();
                if (cursor.getCount()==1 && username.equals(cursor.getString(0)) && password.equals(cursor.getString(1)) ){
                    if (rememberC.isChecked()){
                        SharedPreferences.Editor editor = getSharedPreferences("account",MODE_PRIVATE).edit();
                        editor.putString("username",usernameE.getText().toString());
                        editor.putString("password",passwordE.getText().toString());
                        editor.apply();
                    }
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(gotoListActivity);
                }
                else {
                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}