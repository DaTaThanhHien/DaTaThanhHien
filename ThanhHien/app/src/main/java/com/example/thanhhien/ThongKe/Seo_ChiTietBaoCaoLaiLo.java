package com.example.thanhhien.ThongKe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;
import com.example.thanhhien.SeoCheckConnection;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ChiTietBaoCaoLaiLo extends AppCompatActivity {
    private Toolbar toolbar;
    private long ThuNhapThang,TongChiTrongThang;
    private int mYear, mMonth, mDay,LuuNgayBatDau,LuuNgayKetThuc;
    private Button btnNgayHomNay,btnThangTruoc,btnThangKhac;
    private TextView txtDoanhThuThangNay,txtTongTienDaChi,txtThuNhapThang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_chitietbaocaolailo);
        setTitle("Chi tiết lợi nhuận");
        AnhXa();
        if(isOnline()==false){
            Intent intent=new Intent(Seo_ChiTietBaoCaoLaiLo.this, SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        Date today = new Date();
        final String NgayHomNay = dateFormatter.format(today);

        DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM");
        dateFormatter2.setLenient(false);
        Date today2 = new Date();
        final String ThangNay = dateFormatter2.format(today2);
        getTongTienBanLeTheoThang(Api_custom.laytongtienbanletheongay,ThangNay+"-01",NgayHomNay);

        // biểu đồ
        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);
        mBarChart.addBar(new BarModel("Doanh thu", 400000000,0xFFFFC107));
        mBarChart.addBar(new BarModel("Chi phí",500000000,0xFFF44336));
        mBarChart.addBar(new BarModel("Lợi nhuận", 200000000,0xFF4CAF50));
        mBarChart.startAnimation();
    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTongTienDaChi=findViewById(R.id.txtTongTienDaChi);
        txtThuNhapThang=findViewById(R.id.txtThuNhapThang);
        txtDoanhThuThangNay=findViewById(R.id.txtDoanhThuThangNay);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.nav_themsanpham :
                eventClickChonNgay();

                return  true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_themsanpham,menu);
        menu.getItem(0).setIcon(R.drawable.imgngaykhacchitietlailo);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isOnline()==false){
            Intent intent=new Intent(Seo_ChiTietBaoCaoLaiLo.this, SeoCheckConnection.class);
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
    private void eventClickChonNgay() {
        // khai báo ánh xạ
        View view = getLayoutInflater().inflate(R.layout.bottom_layoutchonthang, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        btnNgayHomNay=view.findViewById(R.id.btnNgayHomNay);
        btnThangTruoc=view.findViewById(R.id.btnThangTruoc);
        btnThangKhac=view.findViewById(R.id.btnThangKhac);

        // khai báo bottom sheet
        final Dialog mBottomSheetDialog = new Dialog(Seo_ChiTietBaoCaoLaiLo.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        // các sự kiên click của bottom sheet
        // lấy ngày hôm nay
        btnNgayHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                dateFormatter.setLenient(false);
                Date today = new Date();
                final String NgayHomNay = dateFormatter.format(today);

                mBottomSheetDialog.dismiss();
            }
        });
        // lấy ngày hôm qua
        btnThangTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy dữ liệu

                // lấy dữ liệu

                mBottomSheetDialog.dismiss();

            }
        });
        // sự kiện lấy bảy ngày qua

        btnThangKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater2 = getLayoutInflater();
                View alertLayout2 = inflater2.inflate(R.layout.ad_item_layoutngaythongke, null);
                final EditText editNgayBatDau = (EditText) alertLayout2.findViewById(R.id.editNgayBatDau);
                final EditText editNgayKetThuc = (EditText) alertLayout2.findViewById(R.id.editNgayKetThuc);
                editNgayBatDau.setInputType(InputType.TYPE_NULL);
                editNgayBatDau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        DatePickerDialog datePickerDialog=new DatePickerDialog(Seo_ChiTietBaoCaoLaiLo.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dateOfMonth) {
                                editNgayBatDau.setText(year+"-"+(monthofYear+1)+"-"+dateOfMonth);
                                LuuNgayBatDau=year+(monthofYear+1)+dateOfMonth;
                            }
                        },calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        );
                        datePickerDialog.show();
                    }
                });
                editNgayKetThuc.setInputType(InputType.TYPE_NULL);
                editNgayKetThuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(Seo_ChiTietBaoCaoLaiLo.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editNgayKetThuc.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                        LuuNgayKetThuc=year+(monthOfYear+1)+dayOfMonth;
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                new SweetAlertDialog(Seo_ChiTietBaoCaoLaiLo.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Chọn Ngày")
                        .setCustomView(alertLayout2)
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                if(LuuNgayBatDau>LuuNgayKetThuc){
                                    new SweetAlertDialog(Seo_ChiTietBaoCaoLaiLo.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Lỗi...")
                                            .setContentText("Ngày kết thúc không nhỏ hơn ngày bắt đầu!!")
                                            .show();
                                }
                                mBottomSheetDialog.dismiss();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }
    // lấy tổng tiền bán lẻ theo tháng
    public void getTongTienBanLeTheoThang(String urlService, final String NgayBatDau, final String NgayKetThuc){
        urlService=urlService+NgayBatDau+"/"+NgayKetThuc;
        RequestQueue requestQueue= Volley.newRequestQueue(Seo_ChiTietBaoCaoLaiLo.this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null&&response.length()!=0){
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    if(jsonObject.getString("TongTien").equals("null")){
                                        ThuNhapThang=ThuNhapThang+0;

                                    }else {
                                        ThuNhapThang=ThuNhapThang+Long.parseLong(jsonObject.getString("TongTien"));
                                    }
                                    getTongTienBanSiTheoThang(Api_custom.laytongtienbansitheongay,NgayBatDau,NgayKetThuc);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ChiTietBaoCaoLaiLo.this, "Lỗi lấy thu nhập ngày", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end


    public void getTongTienBanSiTheoThang(String urlService, String NgayBatDau, String NgayKetThuc){
        urlService=urlService+NgayBatDau+"/"+NgayKetThuc;
        RequestQueue requestQueue= Volley.newRequestQueue(Seo_ChiTietBaoCaoLaiLo.this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null&&response.length()!=0){
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    if(jsonObject.getString("TonTienBanSi").equals("null")){
                                        ThuNhapThang=ThuNhapThang+0;
                                        String TongTienChuyenDoi2= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(ThuNhapThang+""));
                                        txtDoanhThuThangNay.setText(TongTienChuyenDoi2);
                                    }else {
                                        ThuNhapThang=ThuNhapThang+Long.parseLong(jsonObject.getString("TonTienBanSi"));
                                        String TongTienChuyenDoi2= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(ThuNhapThang+""));
                                        txtDoanhThuThangNay.setText(TongTienChuyenDoi2+" VNĐ");

                                    }
                                    DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM");
                                    dateFormatter2.setLenient(false);
                                    Date today2 = new Date();
                                    final String ThangNay = dateFormatter2.format(today2);
                                    getTongTienChiTheoThang(Api_custom.laytongtiendachitheothang,ThangNay,ThangNay);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ChiTietBaoCaoLaiLo.this, "Lỗi lấy thu nhập ngày", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end

    public void getTongTienChiTheoThang(String urlService, final String NgayBatDau, final String NgayKetThuc){
        urlService=urlService+NgayBatDau+"/"+NgayKetThuc;
        RequestQueue requestQueue= Volley.newRequestQueue(Seo_ChiTietBaoCaoLaiLo.this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null&&response.length()!=0){

                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    if(jsonObject.getString("TongChiTrongThang").equals("null")){
                                        txtTongTienDaChi.setText("0 VNĐ");
                                    }else {
                                        TongChiTrongThang=Long.parseLong(jsonObject.getString("TongChiTrongThang"));
                                        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(jsonObject.getString("TongChiTrongThang")));
                                        txtTongTienDaChi.setText(TongTienChuyenDoi+" VNĐ");

                                    }
                                    String TongTienChuyenDoi2= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble((ThuNhapThang-TongChiTrongThang)+""));
                                    if(ThuNhapThang-TongChiTrongThang<=0){
                                        txtThuNhapThang.setText("0 VNĐ");
                                    }else {
                                        txtThuNhapThang.setText(TongTienChuyenDoi2+" VNĐ");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ChiTietBaoCaoLaiLo.this, "Lỗi lấy tổng chi trong tháng", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end







}
