package com.example.thanhhien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.thanhhien.NhapXuatHang.Model_ChonNguyenLieu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String url="http://10.10.20.242:8080/";
    public static ArrayList<Model_ChonNguyenLieu> listSanPhamDaChon=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Seo_GiaoDienLogin.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        }, 1000);
    }

    @Override
    protected void onRestart() {
        finish();
        super.onRestart();
    }
}
