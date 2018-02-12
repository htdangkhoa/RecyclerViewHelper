package com.github.htdangkhoa.library.ItemTouch;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.htdangkhoa.library.Adapter.RecyclerViewHelperAdapter;

/**
 * Created by dangkhoa on 2/11/18.
 */

public class ItemTouchHelperCallback extends android.support.v7.widget.helper.ItemTouchHelper.Callback {
    RecyclerViewHelperAdapter adapter;
    boolean enableDragAndDrop = false;
    boolean enableSwipeToDelete = false;
    int orientation;

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
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        orientation = ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation();

        if (orientation == RecyclerView.HORIZONTAL) {
            return makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        } else {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }
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
            float alpha = 1.0f;
            if (orientation == RecyclerView.HORIZONTAL) {
                alpha = 1 - (Math.abs(dY) / recyclerView.getHeight());
            } else {
                alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
            }
            viewHolder.itemView.setAlpha(alpha);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
