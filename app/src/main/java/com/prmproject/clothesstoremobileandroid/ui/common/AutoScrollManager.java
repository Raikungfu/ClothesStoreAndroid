package com.prmproject.clothesstoremobileandroid.ui.common;

import android.os.Handler;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

public class AutoScrollManager {
    private Handler handler;
    private Runnable runnable;
    private int currentPosition = 0;

    public void startAutoScroll(final RecyclerView recyclerView, final RecyclerView.Adapter adapter, final int scrollInterval) {
        if (adapter == null || adapter.getItemCount() == 0) {
            return;
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition < adapter.getItemCount()) {
                    recyclerView.smoothScrollToPosition(currentPosition);
                    currentPosition++;
                } else {
                    currentPosition = 0;
                    recyclerView.smoothScrollToPosition(currentPosition);
                }

                handler.postDelayed(this, scrollInterval);
            }
        };
        handler.post(runnable);
    }

    public void stopAutoScroll() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public void resetCurrentPosition() {
        currentPosition = 0;
    }
}
