package com.github.htdangkhoa.library.ItemTouch;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dangkhoa on 2/11/18.
 */

public interface ItemTouchListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDirection);
    boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
}
