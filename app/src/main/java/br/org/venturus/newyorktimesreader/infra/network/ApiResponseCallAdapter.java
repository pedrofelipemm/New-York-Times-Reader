package br.org.venturus.newyorktimesreader.infra.network;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import br.org.venturus.newyorktimesreader.infra.exception.NoConnectivityException;
import br.org.venturus.newyorktimesreader.infra.factory.ApiErrorFactory;
import retrofit2.Call;
import retrofit2.CallAdapter;
import timber.log.Timber;

public class ApiResponseCallAdapter<T> implements CallAdapter<T, ApiResponse<T>> {

    private final Type responseType;

    ApiResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public ApiResponse<T> adapt(@NonNull Call<T> call) {
        ApiResponse<T> response;

        try {
            response = new ApiResponse<>(call.execute());
        } catch (NoConnectivityException exception) {
            Timber.e(exception);
            response = new ApiResponse<>(ApiErrorFactory.createNoConnectionError());
        } catch (SocketTimeoutException exception) {
            Timber.e(exception);
            response = new ApiResponse<>(ApiErrorFactory.createTimeoutError());
        } catch (Throwable throwable) {
            Timber.e(throwable);
            response = new ApiResponse<>(throwable);
        }

        return response;
    }
}
