package br.org.venturus.newyorktimesreader.manager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import br.org.venturus.newyorktimesreader.business.ArticleBusiness;
import br.org.venturus.newyorktimesreader.entity.enm.ArticleSection;
import br.org.venturus.newyorktimesreader.entity.to.ArticlesTo;
import br.org.venturus.newyorktimesreader.infra.OperationListener;
import br.org.venturus.newyorktimesreader.infra.OperationResult;
import br.org.venturus.newyorktimesreader.infra.factory.BusinessFactory;
import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.network.NetworkConstants.Error;

public class ArticleManager extends BaseManager {

    private ArticleBusiness articleBusiness;

    public ArticleManager() {
        articleBusiness = BusinessFactory.createArticleBusiness();
    }

    public void loadMostViewed(ArticleSection section, final OperationListener<ArticlesTo> listener) {
        @SuppressLint("StaticFieldLeak")
        final AsyncTask<Void, Integer, OperationResult<ArticlesTo>> task = new AsyncTask<Void, Integer, OperationResult<ArticlesTo>>() {

            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected OperationResult<ArticlesTo> doInBackground(Void... params) {
                return articleBusiness.loadMostViewed(section);
            }

            @Override
            protected void onPostExecute(OperationResult<ArticlesTo> operationResult) {
                removeFromTaskList(this);
                ApiError error = operationResult.getError();

                switch (error.getCodigo()) {
                    case ApiError.NO_ERROR:
                        listener.onSuccess(operationResult.getResult());
                        break;
                    case Error.VALIDATION_CODE:
                        listener.onValidation(operationResult.getValidationCodes());
                        break;
                    default:
                        listener.onError(error);
                }

                listener.onPostExecute();
            }

            @Override
            protected void onCancelled() {
                removeFromTaskList(this);
                listener.onCancel();
            }
        };

        addToTaskList(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void searchArticles(CharSequence query, int page, OperationListener<ArticlesTo> listener) {
        @SuppressLint("StaticFieldLeak")
        final AsyncTask<Void, Integer, OperationResult<ArticlesTo>> task = new AsyncTask<Void, Integer, OperationResult<ArticlesTo>>() {

            @Override
            protected void onPreExecute() {
                listener.onPreExecute();
            }

            @Override
            protected OperationResult<ArticlesTo> doInBackground(Void... params) {
                return articleBusiness.searchArticles(query, page);
            }

            @Override
            protected void onPostExecute(OperationResult<ArticlesTo> operationResult) {
                removeFromTaskList(this);
                ApiError error = operationResult.getError();

                switch (error.getCodigo()) {
                    case ApiError.NO_ERROR:
                        listener.onSuccess(operationResult.getResult());
                        break;
                    case Error.VALIDATION_CODE:
                        listener.onValidation(operationResult.getValidationCodes());
                        break;
                    default:
                        listener.onError(error);
                }

                listener.onPostExecute();
            }

            @Override
            protected void onCancelled() {
                removeFromTaskList(this);
                listener.onCancel();
            }
        };

        addToTaskList(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
