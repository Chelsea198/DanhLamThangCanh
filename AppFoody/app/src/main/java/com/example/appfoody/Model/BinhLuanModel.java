package com.example.appfoody.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BinhLuanModel {
    String noidung,tieude;
    public BinhLuanModel()
    {

    }
    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }
    public  void ThemBinhLuan(String maDanhLam,BinhLuanModel binhLuanModel)
    {
        DatabaseReference nodeBinhLuan= FirebaseDatabase.getInstance().getReference().child("binhluandanhlams");
        String maBinhLuan= nodeBinhLuan.child(maDanhLam).push().getKey();
        nodeBinhLuan.child(maDanhLam).child(maBinhLuan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
