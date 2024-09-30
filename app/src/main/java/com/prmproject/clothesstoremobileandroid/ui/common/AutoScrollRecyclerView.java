package com.prmproject.clothesstoremobileandroid.ui.common;

import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.ui.Adapter.ProductAdapter;

public interface AutoScrollRecyclerView {
    void startAutoScroll(final RecyclerView recyclerView, final RecyclerView.Adapter adapter, final boolean halfScreen);

    void stopAutoScroll();
    boolean shouldScroll(final RecyclerView recyclerView, final RecyclerView.Adapter adapter);
}
