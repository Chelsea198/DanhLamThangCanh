package com.example.appfoody.Control;

import com.example.appfoody.Model.BinhLuanModel;

public class BinhLuanController {
    BinhLuanModel binhLuanModel;
    public  BinhLuanController()
    {
        binhLuanModel= new BinhLuanModel();
    }
    public  void ThemBinhLuan(String maDanhLam,BinhLuanModel binhLuanModel){
        binhLuanModel.ThemBinhLuan(maDanhLam,binhLuanModel);
    }
}
