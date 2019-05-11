package com.example.appfoody.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfoody.Control.BinhLuanController;
import com.example.appfoody.Model.BinhLuanModel;
import com.example.appfoody.R;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenDL,txtDiaChiDanhLam,txtDangBinhLuan;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    String maDanhLam;
    BinhLuanController binhLuanController;
    BinhLuanModel binhLuanModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        maDanhLam=getIntent().getStringExtra("maDanhLam");
        String tenDanhLam=getIntent().getStringExtra("tenDanhLam");
        String diaChi=getIntent().getStringExtra("diaChi");


        binhLuanController= new BinhLuanController();
        AddControl();
        txtDiaChiDanhLam=findViewById(R.id.txtDiaChiDanhLam);
        txtTenDL=findViewById(R.id.txtTenDL);

        txtDiaChiDanhLam.setText(diaChi);
        txtTenDL.setText(tenDanhLam);

        txtDangBinhLuan=findViewById(R.id.txtDangBinhLuan);
        txtDangBinhLuan.setOnClickListener(this);

    }
    public  void AddControl()
    {

        edNoiDungBinhLuan=findViewById(R.id.edNoiDungBinhLuan);
        edTieuDeBinhLuan=findViewById(R.id.edTieuDeBinhLuan);
    }
    public  void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.txtDangBinhLuan:
                binhLuanModel= new BinhLuanModel();
                String tieude=edTieuDeBinhLuan.getText().toString();
                String noidung=edNoiDungBinhLuan.getText().toString();

                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setTieude(tieude);

                binhLuanController.ThemBinhLuan(maDanhLam,binhLuanModel);

                break;
        }
    }
}
