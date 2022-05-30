package com.miaomi.fenbei.base.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class PreviewBean implements Parcelable {
    private String url;
    //类型，1：视频，2：图片
    private int type = 2;



    public PreviewBean(String url, int type) {
        this.url = url;
        this.type = type;
    }

    protected PreviewBean(Parcel in) {
        url = in.readString();
        type = in.readInt();
    }

    public static final Creator<PreviewBean> CREATOR = new Creator<PreviewBean>() {
        @Override
        public PreviewBean createFromParcel(Parcel in) {
            return new PreviewBean(in);
        }

        @Override
        public PreviewBean[] newArray(int size) {
            return new PreviewBean[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(type);
    }
}
