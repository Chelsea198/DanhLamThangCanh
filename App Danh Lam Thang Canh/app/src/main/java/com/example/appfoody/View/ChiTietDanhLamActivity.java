package com.example.appfoody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ChiTietDanhLamActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView txtTenDanhLamThangCanh,txtDiaChiDanhLamThangCanh;
    DanhLamThangCanhModel danhLamThangCanhModel;
    ImageView imgHinhAnhDanhLam;
    MapFragment mapFragment;
    GoogleMap googleMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietdanhlam);

        danhLamThangCanhModel=getIntent().getParcelableExtra("danhlamthangcanh");


        AddControl();
        HienThiChiTietDanhLam();
    }
    public  void AddControl(){
        txtDiaChiDanhLamThangCanh=findViewById(R.id.txtDiaChiDanhLamThangCanh);
        txtTenDanhLamThangCanh=findViewById(R.id.txtTenDanhLamThangCanh);
        imgHinhAnhDanhLam=findViewById(R.id.imgHinhAnhDanhLam);
        mapFragment=(MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    public void HienThiChiTietDanhLam()
    {
        txtTenDanhLamThangCanh.setText(danhLamThangCanhModel.getTendanhlam());
        txtDiaChiDanhLamThangCanh.setText(danhLamThangCanhModel.getDiachi());
        StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(danhLamThangCanhModel.getHinhanhdanhlam().get(0));
        long ONE_MEGABYTE = 5 * 1024 * 1024;
        storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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
}
