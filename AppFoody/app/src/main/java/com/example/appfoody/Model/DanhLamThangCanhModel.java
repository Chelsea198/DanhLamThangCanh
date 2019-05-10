package com.example.appfoody.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.appfoody.Control.Interfaces.DanhLamInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhLamThangCanhModel implements  Parcelable{
    String tendanhlam,madanhlam,diachi,gioithieu;
    double longtitude,latitude;
    List<String> hinhanhdanhlam;

    protected DanhLamThangCanhModel(Parcel in) {
        tendanhlam = in.readString();
        madanhlam = in.readString();
        diachi = in.readString();
        gioithieu = in.readString();
        longtitude = in.readDouble();
        latitude = in.readDouble();
        hinhanhdanhlam = in.createStringArrayList();
    }

    public static final Creator<DanhLamThangCanhModel> CREATOR = new Creator<DanhLamThangCanhModel>() {
        @Override
        public DanhLamThangCanhModel createFromParcel(Parcel in) {
            return new DanhLamThangCanhModel(in);
        }

        @Override
        public DanhLamThangCanhModel[] newArray(int size) {
            return new DanhLamThangCanhModel[size];
        }
    };

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }



    public String getMadanhlam() {
        return madanhlam;
    }

    public void setMadanhlam(String madanhlam) {
        this.madanhlam = madanhlam;
    }
    DatabaseReference nodeRoot;

    public DanhLamThangCanhModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }
    public String getTendanhlam() {
        return tendanhlam;
    }


    public void setTendanhlam(String tendanhlam) {
        this.tendanhlam = tendanhlam;
    }

    public List<String> getHinhanhdanhlam() {
        return hinhanhdanhlam;
    }

    public void setHinhanhdanhlam(List<String> hinhanhdanhlam) {
        this.hinhanhdanhlam = hinhanhdanhlam;
    }
    public void getDanhSachDanhLamThangCanh(final DanhLamInterface danhLamInterface) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Lấy danh sách danh lam thắng cảnh
                DataSnapshot dataSnapshotDanhLam = dataSnapshot.child("danhlamthangcanhs");
                for (DataSnapshot valueDanhLam : dataSnapshotDanhLam.getChildren()
                     ) {
                    DanhLamThangCanhModel danhLamThangCanhModel = valueDanhLam.getValue(DanhLamThangCanhModel.class);

                    danhLamThangCanhModel.setMadanhlam(valueDanhLam.getKey());
                    //Lấy danh sách hình ảnh danh lam  theo mã
                    DataSnapshot dataSnapshotHinhDanhLam = dataSnapshot.child("hinhanhdanhlams").child(danhLamThangCanhModel.getMadanhlam());
                    List<String> hinhAnhList = new ArrayList<>();
                    for (DataSnapshot valueHinhDanhLam : dataSnapshotHinhDanhLam.getChildren()
                         ) {
                        hinhAnhList.add(valueHinhDanhLam.getValue(String.class));
                    }
                    danhLamThangCanhModel.setHinhanhdanhlam(hinhAnhList);

                    danhLamInterface.getDanhSachDanhLamThangCanh(danhLamThangCanhModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(tendanhlam);
        dest.writeString(madanhlam);
        dest.writeString(diachi);
        dest.writeString(gioithieu);
        dest.writeDouble(longtitude);
        dest.writeDouble(latitude);
        dest.writeStringList(hinhanhdanhlam);
    }


}
