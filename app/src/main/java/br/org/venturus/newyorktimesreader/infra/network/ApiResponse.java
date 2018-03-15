package br.org.venturus.newyorktimesreader.infra.network;

import br.org.venturus.newyorktimesreader.infra.factory.ApiErrorFactory;
import retrofit2.Response;

public class ApiResponse<T> {

    private final int httpCode;

    private final T result;

    private final ApiError error;

    ApiResponse(Response<T> response) {
        httpCode = response.code();

        if (response.isSuccessful()) {
            result = response.body();
            error = new ApiError();
        } else {
            result = null;
            error = ApiUtils.parseError(response.errorBody());
        }
    }

    ApiResponse(ApiError error) {
        httpCode = 500;
        result = null;
        this.error = error;
    }

    ApiResponse(Throwable t) {
        httpCode = 500;
        result = null;
        error = ApiErrorFactory.createInternalServerErrorError();
    }

    public boolean isSuccessful() {
        return httpCode >= 200 && httpCode < 300;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public T getResult() {
        return result;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public ApiError getError() {
        return error;
    }
}

