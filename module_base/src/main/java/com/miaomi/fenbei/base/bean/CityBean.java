package com.miaomi.fenbei.base.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class CityBean implements IPickerViewData {
    public String name;
    public List<Shi> city;
    public static class Shi{
        public String name;
        public List<String>area;

    }
    //  这个要返回省的名字
    @Override
    public String getPickerViewText() {
        return this.name;
    }

}
