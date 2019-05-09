package com.example.appfoody.Control;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appfoody.Adapters.AdapterRecyclerOdau;
import com.example.appfoody.Control.Interfaces.DanhLamInterface;
import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;

import java.util.ArrayList;
import java.util.List;

public class DanhLamController {
    Context context;
    DanhLamThangCanhModel danhLamThangCanhModel;
    AdapterRecyclerOdau adapterRecyclerOdau;

    public DanhLamController(Context context){
        this.context = context;
        danhLamThangCanhModel = new DanhLamThangCanhModel();
    }

    public void getDanhSachQuanAnController(Context context,RecyclerView recyclerOdau){

        final List<DanhLamThangCanhModel> danhLamThangCanhModelList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context, danhLamThangCanhModelList, R.layout.custom_layout_recyclerview_danhlam);
        recyclerOdau.setAdapter(adapterRecyclerOdau);
        DanhLamInterface danhLamInterface = new DanhLamInterface() {
            @Override
            public void getDanhSachDanhLamThangCanh(DanhLamThangCanhModel danhLamThangCanhModel) {
                danhLamThangCanhModelList.add(danhLamThangCanhModel);
                adapterRecyclerOdau.notifyDataSetChanged();
            }
        };
        danhLamThangCanhModel.getDanhSachDanhLamThangCanh(danhLamInterface);
    }
}
