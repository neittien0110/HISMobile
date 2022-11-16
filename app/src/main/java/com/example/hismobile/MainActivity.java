package com.example.hismobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    /**
     * Biến điều khiển webview
     */
    WebView web;

    /**
     * Biến điều khiển ô nhập URL
     */
    EditText editTextURL;
    /**
     * URL cần truy cập
     */
    static String myURL;

    private String filename = "config.json";
    private String filepath = "MyWebView";
    File myExternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Gán điều khiển cho đối tượng giao diện webview có tên GeneralWebView  */
        web = findViewById(R.id.GeneralWebView);
        /** Lấy cấu hình webview */
        WebSettings webSettings = web.getSettings();
        /** Luôn cho phép javascript được hoạt động */
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        /** Gán điều khiển cho đối tượng giao diện editor có tên TextURL  */
        editTextURL = findViewById(R.id.TextURL);

        /* Đọc URL mặc định từ file */
        myURL = "";
        try {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myURL = myURL + strLine;
            }
            in.close();
        } catch (IOException e) {
            myURL = "http://192.161.0.101:3000";
            e.printStackTrace();
        }

        /** Gán giá trị URL mặc định */
        editTextURL.setText(myURL);

        /** Xem luôn */
        onGoToHISCloud(null);
    }

    /**
     * Sự kiện khi người dùng bấm nút thay đổi URL của Webview
     * @param view
     */
    public void onGoToHISCloud(View view) {
        /* Lấy URL cần hiển thị */
        myURL = editTextURL.getText().toString();
        /* Hiển thị lên webview */
        web.loadUrl(myURL);

        /* Đồng thời lưu lại ra file */
        try {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
            FileOutputStream fos = new FileOutputStream(myExternalFile);
            fos.write(myURL.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}