package com.example.appfoody.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import com.example.appfoody.Control.DanhLamController;
import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;

public class TrangChuActivity extends AppCompatActivity{
    RecyclerView recyclerViewdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        recyclerViewdata=findViewById(R.id.recylerOdau);

    }

    @Override
    protected void onStart() {
        super.onStart();
        DanhLamController odauControl=new DanhLamController(getBaseContext());
        odauControl.getDanhSachQuanAnController(this,recyclerViewdata);
    }
}
