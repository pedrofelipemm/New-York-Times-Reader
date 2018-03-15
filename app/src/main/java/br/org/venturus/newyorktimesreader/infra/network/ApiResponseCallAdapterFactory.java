package br.org.venturus.newyorktimesreader.infra.network;

import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ApiResponseCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (getRawType(returnType) != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }

        return new ApiResponseCallAdapter<>(getParameterUpperBound(0, (ParameterizedType) returnType));
    }
}
