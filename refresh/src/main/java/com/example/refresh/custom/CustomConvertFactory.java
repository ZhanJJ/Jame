package com.example.refresh.custom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by James on 2017/1/6.
 */

public class CustomConvertFactory extends Converter.Factory {
    public CustomConvertFactory() {
        super();
    }

    public static CustomConvertFactory create(){
        return new CustomConvertFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new CustomResponseBodyConverter<JSONObject>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
    
    public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T>{

        @Override
        public T convert(ResponseBody value) throws IOException {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(value.string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T)jsonObject;
        }
    }
}
