package br.org.venturus.newyorktimesreader.ui;


import android.os.AsyncTask;

import br.org.venturus.newyorktimesreader.infra.OperationListener;

public class OnDisplayFeedback extends AsyncTask<Void, Void, Void> {


    private OperationListener operationListener;

    public OnDisplayFeedback(OperationListener operationListener) {
        this.operationListener = operationListener;
    }

    @Override
    protected void onPreExecute() {
        operationListener.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        operationListener.onPostExecute();
    }

}
