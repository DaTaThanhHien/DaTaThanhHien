package com.example.thanhhien.ThongKe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.sdsmdg.tastytoast.TastyToast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_QuanLyThongKe extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView btnChonNgayKhac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_quanlythongke);
        setTitle("Thống kê");
        AnhXa();
        enventClick();
        // biểu đồ sản phẩm bán chạy
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("Sắt V", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sắt U20", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Tôn lạnh xanh", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Sắt V20", 9, Color.parseColor("#FED70E")));
        mPieChart.startAnimation();

    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnChonNgayKhac=findViewById(R.id.btnChonNgayKhac);




    }
    private void enventClick(){
        btnChonNgayKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickChonNgay();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventClickChonNgay() {
        // khai báo ánh xạ
        View view = getLayoutInflater().inflate(R.layout.item_layoutchonngay, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        // khai báo bottom sheet
        final Dialog mBottomSheetDialog = new Dialog(Seo_QuanLyThongKe.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        // sét các điều kiện click
        // sự kiện đóng bottom sheet
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
    }





}
