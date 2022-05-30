package com.miaomi.fenbei.base.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MrRequestFactory extends Converter.Factory {
    public static MrRequestFactory create() {
        return create(new GsonBuilder().setLenient().create());
    }

    public static MrRequestFactory create(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        return new MrRequestFactory(gson);
    }

    private final Gson gson;

    private MrRequestFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MrResponseConverter<>(gson, adapter); // 响应
    }

}
