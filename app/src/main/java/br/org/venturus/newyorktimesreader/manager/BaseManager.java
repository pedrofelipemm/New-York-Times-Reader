package br.org.venturus.newyorktimesreader.manager;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseManager {

    private final List<AsyncTask<?, ?, ?>> taskList;

    protected BaseManager() {
        taskList = new ArrayList<>();
    }

    public void cancelOperations() {
        for (AsyncTask<?, ?, ?> task : taskList) {
            task.cancel(false);
        }
    }

    protected void addToTaskList(AsyncTask<?, ?, ?> task) {
        taskList.add(task);
    }

    protected void removeFromTaskList(AsyncTask<?, ?, ?> task) {
        taskList.remove(task);
    }
}