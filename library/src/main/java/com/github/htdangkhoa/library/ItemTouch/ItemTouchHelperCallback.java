package com.github.htdangkhoa.library.ItemTouch;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

import com.github.htdangkhoa.library.Adapter.RecyclerViewHelperAdapter;

/**
 * Created by dangkhoa on 2/11/18.
 */

public class ItemTouchHelperCallback extends android.support.v7.widget.helper.ItemTouchHelper.Callback {
    RecyclerViewHelperAdapter adapter;
    boolean enableDragAndDrop = false;
    boolean enableSwipeToDelete = false;

    public void setAdapter(RecyclerViewHelperAdapter adapter) {
        this.adapter = adapter;
    }

    public void setEnableDragAndDrop() {
        this.enableDragAndDrop = true;
    }

    public void setEnableSwipeToDelete() {
        this.enableSwipeToDelete = true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return enableDragAndDrop;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = android.support.v7.widget.helper.ItemTouchHelper.UP | android.support.v7.widget.helper.ItemTouchHelper.DOWN;
        int swipeFlags = android.support.v7.widget.helper.ItemTouchHelper.START | android.support.v7.widget.helper.ItemTouchHelper.END;
        return makeMovementFlags((enableDragAndDrop) ? dragFlags : 0, (enableSwipeToDelete) ? swipeFlags : 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (enableDragAndDrop) adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (enableSwipeToDelete) adapter.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (enableSwipeToDelete && actionState == android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE) {
            float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
            viewHolder.itemView.setAlpha(alpha);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
