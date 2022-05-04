package com.example.finalwork;

import static com.example.finalwork.SQLiteHelper.insertUser;

import androidx.appcompat.app.AppCompatActivity;

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
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameE.getText().toString();
                String password = passwordE.getText().toString();
                if (username.equals("") || password.equals("")){
                    Toast.makeText(MainActivity.this,"用户名或密码不可为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Cursor cursor = db.query("user",)
                    insertUser(db,username,password);
                    Toast.makeText(MainActivity.this,"注册成功，点击登录进入",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录流程
                String username = usernameE.getText().toString();
                String password = passwordE.getText().toString();
                Cursor cursor = db.query("user", null,"username=?", new String[]{username},null,null,null);
            }
        });

    }
}