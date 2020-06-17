package com.example.thanhhien;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sdsmdg.tastytoast.TastyToast;

import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_GiaoDienLogin extends AppCompatActivity {
    private EditText edit_TaiKhoan,edit_MatKhau;
    private Button btnDangNhap;
    private  SweetAlertDialog pDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_giaodienlogin);
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        if(isOnline()==false){
            Intent intent=new Intent(Seo_GiaoDienLogin.this,SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        AnhXa();
        Onclick();
    }

    private void AnhXa() {
        edit_TaiKhoan=(EditText) findViewById(R.id.edit_TaiKhoan);
        edit_MatKhau=(EditText)findViewById(R.id.edit_MatKhau);
        btnDangNhap=findViewById(R.id.btnDangNhap2);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isOnline()==false){
            Intent intent=new Intent(Seo_GiaoDienLogin.this,SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }
    }

    // check connect
    public boolean isOnline() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);
        }
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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
           public void onClick(View view) {
               if(KiemTra()==false){
                   return;
               }else {
                   pDialog = new SweetAlertDialog(Seo_GiaoDienLogin.this, SweetAlertDialog.PROGRESS_TYPE);
                   pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                   pDialog.setTitleText("Loading ...");
                   pDialog.setCancelable(true);
                   pDialog.show();

                   Login(Api_custom.Login);


               }
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
    public void Login(String urlService){
        urlService=urlService+edit_TaiKhoan.getText().toString();
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response!=null&&response.length()!=0){
                            pDialog.cancel();
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    if(jsonObject.getString("psw").equalsIgnoreCase(edit_MatKhau.getText().toString())){
                                        Intent intent=new Intent(Seo_GiaoDienLogin.this,Seo_GiaoDienChinh.class);
                                        startActivity(intent);
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }else {
                                        TastyToast.makeText(getApplicationContext(), "Mật khẩu không chính xác", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            pDialog.cancel();
                            TastyToast.makeText(getApplicationContext(), "Tài khoản không tồn tại", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        TastyToast.makeText(getApplicationContext(), "Lỗi không thể kết nối sever !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }//end

}
