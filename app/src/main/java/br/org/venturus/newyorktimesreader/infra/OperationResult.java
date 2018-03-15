package br.org.venturus.newyorktimesreader.infra;

import java.util.List;

import br.org.venturus.newyorktimesreader.infra.network.ApiError;

public class OperationResult<T> {

    private T result;
    private List<String> validationCodes;
    private ApiError error = new ApiError();

    @SuppressWarnings({"unused", "WeakerAccess"})
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public List<String> getValidationCodes() {
        return validationCodes;
    }

    public void setValidationCodes(List<String> validationCodes) {
        this.validationCodes = validationCodes;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ " +
                "result=" + result + " " +
                "validationCodes=" + validationCodes + " " +
                "error=" + error + " " +
                " ]";
    }

}
