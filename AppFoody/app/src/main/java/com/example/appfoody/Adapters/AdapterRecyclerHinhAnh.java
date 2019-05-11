package com.example.appfoody.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appfoody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterRecyclerHinhAnh extends RecyclerView.Adapter<AdapterRecyclerHinhAnh.ViewHinhAnh> {

    Context context;
    int resource;
    List<String> listHinh;
    public  AdapterRecyclerHinhAnh(  Context context,int resource, List<String> listHinh)
    {
        this.context=context;
        this.resource=resource;
        this.listHinh=listHinh;
    }
    public class ViewHinhAnh extends RecyclerView.ViewHolder {
        ImageView imgHinhAnhDep;
        public ViewHinhAnh(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhDep=itemView.findViewById(R.id.imgHinhanhDep);
        }
    }
    @NonNull
    @Override
    public ViewHinhAnh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        ViewHinhAnh viewHinhAnh=new ViewHinhAnh(view);
        return  viewHinhAnh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHinhAnh viewHinhAnh, int i) {
        for(String linkHinh : listHinh) {
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(linkHinh);
            long ONE_MEGABYTE = 5 * 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    viewHinhAnh.imgHinhAnhDep.setImageBitmap(bitmap);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return listHinh.size();
    }

}
