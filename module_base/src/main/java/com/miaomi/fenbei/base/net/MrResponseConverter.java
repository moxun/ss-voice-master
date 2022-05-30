package com.miaomi.fenbei.base.net;



import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.miaomi.fenbei.base.bean.ApitData;
import com.miaomi.fenbei.base.bean.NetBaseBean;
import com.miaomi.fenbei.base.util.EncryptUtil;
import com.miaomi.fenbei.base.util.LogUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MrResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public MrResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String string = value.string();
        LogUtil.INSTANCE.i(string);
        NetBaseBean baseBean = new Gson().fromJson(string, NetBaseBean.class);
        if (TextUtils.isEmpty(baseBean.getDecrypt())){
            ApitData apitData = new Gson().fromJson(string,ApitData.class);
            if (apitData.getCode() != 0){
                NetErrorException errorException = new NetErrorException();
                errorException.setCode(apitData.getCode());
                errorException.setMsg(apitData.getMsg());
                throw errorException;
            }else{
                string = EncryptUtil.aesDecrypt(apitData.getData());

            }
        }else{
            string = EncryptUtil.aesDecrypt(baseBean.getDecrypt());
        }
        LogUtil.INSTANCE.i(string);
//        ApitData apitData = new Gson().fromJson(string,ApitData.class);
//        if (apitData.getCode() != 0){
//            NetErrorException errorException = new NetErrorException();
//            errorException.setCode(apitData.getCode());
//            errorException.setMsg(apitData.getMsg());
//            throw errorException;
//        }else{
//            string = apitData.getData();
//        }
        LogUtil.INSTANCE.i(string);
        return adapter.fromJson(string);
    }
}
