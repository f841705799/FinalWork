package com.example.finalwork;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import com.example.finalwork.SQLiteHelper;

public class AddBook extends AppCompatActivity {

    private ImageButton imageButton;
    private Button Addbook_B;
    private Intent gobacktoList;
    private EditText ISBN_E,name_E,author_E,publishing_E;
    private ImageView imageView;
    private String username,name,author,authorIntro,photoUrl,publishing,published,description;
    private long ISBN;
    private Integer douban;
    private Integer doubanScore;
    private ProgressDialog progressDialog;


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(AddBook.this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(AddBook.this, "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(AddBook.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    ISBN_E.setText(result.getContents());
                    //sendRequestWithHttpURLConnection(result.getContents());
                }
            });
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (ISBN_E.getText().toString().length() == 13) {
                ISBN = Long.parseLong(ISBN_E.getText().toString());
                progressDialog.show();
                sendRequestWithHttpURLConnection(ISBN);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        SQLiteOpenHelper dbHelper = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        username=MainActivity.username;
        gobacktoList = new Intent(this,BookList.class);
        imageButton = (ImageButton) findViewById(R.id.Button_scan);
        Addbook_B = (Button) findViewById(R.id.Button_add);
        ISBN_E = (EditText) findViewById(R.id.Edit_ISBN);
        name_E = (EditText) findViewById(R.id.Edit_name);
        author_E = (EditText) findViewById(R.id.Edit_author);
        publishing_E = (EditText) findViewById(R.id.Edit_publishing);
        progressDialog=new ProgressDialog(AddBook.this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false); //如果设置为false，则不可以使用back键返回
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ScanOptions options = new ScanOptions();
                ScanOptions options = new ScanOptions().setCaptureActivity(ScanCode.class);
                options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setBarcodeImageEnabled(false);
                barcodeLauncher.launch(options);
            }
        });
        Addbook_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteHelper.insertBook(db,ISBN,name,author,authorIntro,photoUrl,publishing,published,description,douban,doubanScore);
                SQLiteHelper.insertBookRecord(db,username,ISBN);
                Toast.makeText(AddBook.this,"添加成功",Toast.LENGTH_SHORT).show();
                startActivity(gobacktoList);
            }
        });
        ISBN_E.addTextChangedListener(watcher);
    }


    private void sendRequestWithHttpURLConnection(long ISBN){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://api.jike.xyz/situ/book/isbn/"+ String.valueOf(ISBN) +"?apikey=12560.bcbb69cfb71a40082e1585b5106d7777.8b26d60f99bdef5866e7fdbedb9e6a2a");
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                    response.append(line);
                    }
                    Log.v("Internet",response.toString());
                    parseJSONWithJSONObject(response.toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            //设置文本框显示9787020024759
                name_E.setText(name);
                author_E.setText(author);
                publishing_E.setText(publishing);
                progressDialog.hide();
            }
        });
    }
    private String parseJSONWithJSONObject(String jsonData) {
        String ret = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("ret")==0 && jsonObject.getString("msg").equals("请求成功")){
                JSONObject data = new JSONObject(jsonObject.getString("data"));
                name = data.getString("name");
                author = data.getString("author");
                publishing = data.getString("publishing");
                published = data.getString("published");
                douban = data.getInt("douban");
                doubanScore = data.getInt("doubanScore");
                photoUrl = data.getString("photoUrl");
                authorIntro = data.getString("authorIntro");
                description = data.getString("description");
                showResponse();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}