package com.example.appfoody.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfoody.Adapters.AdapterRecyclerHinhAnh;
import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDanhLamActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView txtTenDanhLamThangCanh,txtDiaChiDanhLamThangCanh,txtDanhGia,txtDanhDau,txtTieuDeToolbar,txtGioiThieu;
    DanhLamThangCanhModel danhLamThangCanhModel;
    ImageView imgHinhAnhDanhLam;
    MapFragment mapFragment;
    GoogleMap googleMap;
    RecyclerView recyclerViewAnhDep;
    AdapterRecyclerHinhAnh adapterRecyclerHinhAnh;
    FirebaseUser user;
    ArrayList<Bitmap> bitmapList= new ArrayList<>();
    ArrayList<String> listHinh=new ArrayList<>();
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietdanhlam);

        danhLamThangCanhModel=getIntent().getParcelableExtra("danhlamthangcanh");
        mContext=this;

        AddControl();
        HienThiChiTietDanhLam();
        HienThiHinhAnhDep();
        AddEvents();

    }
    public  void AddControl(){
        txtGioiThieu=findViewById(R.id.txtGioiThieu);
        txtTieuDeToolbar=findViewById(R.id.txtTieuDeToolbar);
        txtDanhGia=findViewById(R.id.txtDanhGia);
        txtDanhDau=findViewById(R.id.txtDanhDau);
        txtDiaChiDanhLamThangCanh=findViewById(R.id.txtDiaChiDanhLamThangCanh);
        txtTenDanhLamThangCanh=findViewById(R.id.txtTenDanhLamThangCanh);
        imgHinhAnhDanhLam=findViewById(R.id.imgHinhAnhDanhLam);
        recyclerViewAnhDep=findViewById(R.id.recyclerHinhAnhDep);
        mapFragment=(MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    public void HienThiChiTietDanhLam()
    {
        txtGioiThieu.setText(danhLamThangCanhModel.getGioithieu());
        txtTenDanhLamThangCanh.setText(danhLamThangCanhModel.getTendanhlam());
        txtDiaChiDanhLamThangCanh.setText(danhLamThangCanhModel.getDiachi());
        txtTieuDeToolbar.setText(danhLamThangCanhModel.getTendanhlam());
        StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(danhLamThangCanhModel.getHinhanhdanhlam().get(0));
        long ONE_MEGABYTE = 5 * 1024 * 1024;
        storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhAnhDanhLam.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgHinhAnhDanhLam.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        double longtitude=danhLamThangCanhModel.getLongtitude();
        Log.d("kt",danhLamThangCanhModel.getLongtitude()+"");
        double latitude=danhLamThangCanhModel.getLatitude();
        LatLng latLng=new LatLng(latitude,longtitude);
        MarkerOptions markerOptions= new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(danhLamThangCanhModel.getTendanhlam());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.moveCamera(cameraUpdate);

    }
    public void HienThiHinhAnhDep()
    {
        recyclerViewAnhDep.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        listHinh=danhLamThangCanhModel.getHinhanhdanhlam();

        for (String imageChild:listHinh) {

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(imageChild);
            long ONE_MEGABYTE = 5 * 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size()==listHinh.size())
                    {
                        recyclerViewAnhDep.setAdapter(new AdapterRecyclerHinhAnh(mContext,R.layout.custom_layout_hinhanhdep, listHinh,bitmapList));

                    }

                }
            });
        }

    }
    public void AddEvents(){
        txtDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AccessToken accessToken =AccessToken.getCurrentAccessToken();
                user =FirebaseAuth.getInstance().getCurrentUser();
                if(user==null)
                {
                    Intent iBinhLuan=new Intent(ChiTietDanhLamActivity.this,BinhLuanActivity.class);
                    iBinhLuan.putExtra("maDanhLam",danhLamThangCanhModel.getMadanhlam());
                    iBinhLuan.putExtra("tenDanhLam",danhLamThangCanhModel.getTendanhlam());
                    iBinhLuan.putExtra("diaChi",danhLamThangCanhModel.getDiachi());
                    startActivity(iBinhLuan);
                }else {
                    Toast.makeText(ChiTietDanhLamActivity.this,"Bạn Cần Đăng Nhập",Toast.LENGTH_SHORT).show();
                    Intent iDangNhap=new Intent(ChiTietDanhLamActivity.this,DangNhapActivity.class);
                    startActivity(iDangNhap);

                }
            }
        });
    }

}
