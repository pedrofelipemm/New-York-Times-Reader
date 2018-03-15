package br.org.venturus.newyorktimesreader.infra;

import java.util.List;

import br.org.venturus.newyorktimesreader.infra.network.ApiError;

public abstract class OperationListener<T> {

    public void onPreExecute(){}

    public void onSuccess(T result){}

    public void onValidation(List<String> codes){}

    public void onError(ApiError error){}

    public void onCancel(){}

    public void onProgressUpdate(int progress){}

    public void onPostExecute(){}

}
