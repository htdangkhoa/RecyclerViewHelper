package com.github.htdangkhoa.library.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by dangkhoa on 2/11/18.
 */

public abstract class RecyclerViewHelperAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements RecyclerViewHelperAdapterListener {
    List<?> objects;
    ItemTouchHelper itemTouchHelper;

    public ItemTouchHelper getItemTouchHelper() {
        return itemTouchHelper;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public RecyclerViewHelperAdapter(List<?> objects) {
        this.objects = objects;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(objects, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(objects, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemove(int position) {
        objects.remove(position);
        notifyItemRemoved(position);
    }
}
