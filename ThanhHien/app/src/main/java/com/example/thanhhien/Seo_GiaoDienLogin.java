package com.example.thanhhien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_GiaoDienLogin extends AppCompatActivity {
    private EditText edit_TaiKhoan,edit_MatKhau;
    private Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.seo_giaodienlogin);
        AnhXa();
        Onclick();






    }

    private void AnhXa() {
        edit_TaiKhoan=(EditText) findViewById(R.id.edit_TaiKhoan);
        edit_MatKhau=(EditText)findViewById(R.id.edit_MatKhau);
        btnDangNhap=(Button) findViewById(R.id.btnDangNhap);
    }
    private boolean KiemTra(){
        String kitudacbiet="^[a-zA-Z0-9]{1,60}$";
        String mUsname=edit_TaiKhoan.getText().toString().trim();
        String mPassword=edit_MatKhau.getText().toString().trim();
        if(mUsname.length()==0){
            new SweetAlertDialog(Seo_GiaoDienLogin.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Lỗi")
                    .setContentText("Vui lòng nhập tài khoản!")
                    .show();
            return false;
        }
        if (mPassword.length()==0){
            new SweetAlertDialog(Seo_GiaoDienLogin.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Lỗi")
                    .setContentText("Vui lòng nhập mật khẩu!")
                    .show();
            return false;
        }
        if(!mUsname.matches(kitudacbiet)){
            new SweetAlertDialog(Seo_GiaoDienLogin.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Lỗi")
                    .setContentText("Tài khoản không được nhập kí tự đặc biệt!")
                    .show();
            return false;
        }
        if(!mPassword.matches(kitudacbiet)){
            new SweetAlertDialog(Seo_GiaoDienLogin.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Lỗi")
                    .setContentText("Mật khẩu không được nhập kí tự đặc biệt!")
                    .show();
            return false;
        }
        return true;
    }
    private void Onclick(){
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KiemTra()==false){ return;
                }
                Intent intent=new Intent(Seo_GiaoDienLogin.this,Seo_GiaoDienChinh.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    btnDangNhap.setTextColor(Color.parseColor("#2196F3"));
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    btnDangNhap.setTextColor(Color.WHITE);
                }
                return false;
            }
        });
    }
}
