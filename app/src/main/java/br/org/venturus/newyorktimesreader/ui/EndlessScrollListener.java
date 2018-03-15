package br.org.venturus.newyorktimesreader.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.widget.AbsListView;

public abstract class EndlessScrollListener extends OnScrollListener implements AbsListView.OnScrollListener {

    private int maxItemsPerRequest;
    private LinearLayoutManager layoutManager;

    public EndlessScrollListener(int maxItemsPerRequest, LinearLayoutManager layoutManager) {
        this.maxItemsPerRequest = maxItemsPerRequest;
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (canLoadMoreItems() && !isLoading()) {
            onScrolledToEnd(layoutManager.findFirstVisibleItemPosition());
        }
    }

    protected abstract boolean isLoading();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    protected void refreshView(RecyclerView view, RecyclerView.Adapter adapter, int position) {
        view.setAdapter(adapter);
        view.invalidate();
        view.scrollToPosition(position);
    }

    protected boolean canLoadMoreItems() {
        int visibleItemsCount = layoutManager.getChildCount();
        int totalItemsCount = layoutManager.getItemCount();
        int pastVisibleItemsCount = layoutManager.findFirstVisibleItemPosition();

        boolean lastItemShown = visibleItemsCount + pastVisibleItemsCount >= totalItemsCount;

        return lastItemShown && totalItemsCount >= maxItemsPerRequest;
    }

    public abstract void onScrolledToEnd(int firstVisibleItemPosition);

}