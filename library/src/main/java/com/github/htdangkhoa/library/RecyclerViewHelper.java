package com.github.htdangkhoa.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.htdangkhoa.library.Adapter.RecyclerViewHelperAdapter;
import com.github.htdangkhoa.library.Decoration.DividerItemDecoration;
import com.github.htdangkhoa.library.ItemTouch.ItemTouchHelperCallback;

/**
 * Created by dangkhoa on 2/11/18.
 */

public class RecyclerViewHelper {
    private Context context;
    private RecyclerView recyclerView;
    private int orientation = 1;
    private boolean reverseLayout = false;
    private RecyclerViewHelperAdapter adapter;

    private ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback();
    private boolean enableSwipeToDelete = false;
    private boolean enableDragAndDrop = false;

    public RecyclerViewHelper setContext(Context context) {
        this.context = context;
        return this;
    }

    public RecyclerViewHelper setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public RecyclerViewHelper setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL)
            new RuntimeException("Invalid orientation:" + orientation);

        this.orientation = orientation;
        return this;
    }

    public RecyclerViewHelper setReverseLayout() {
        this.reverseLayout = true;
        return this;
    }

    public RecyclerViewHelper setDivider() {
        /**
         * Check Context.
         * */
        checkContext();

        /**
         * Check RecyclerView.
         * */
        checkRecyclerView();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return this;
    }

    public RecyclerViewHelper setAdapter(@NonNull RecyclerViewHelperAdapter adapter) {
        this.adapter = adapter;
        itemTouchHelperCallback.setAdapter(adapter);
        return this;
    }

    public RecyclerViewHelper enableSwipeToDelete() {
        this.enableSwipeToDelete = true;
        return this;
    }

    public RecyclerViewHelper enableDragAndDrop() {
        this.enableDragAndDrop = true;
        return this;
    }

    public RecyclerViewHelper enableStickyHeader() {
        return this;
    }

    public RecyclerViewHelper build() {
        /**
         * Check Context.
         * */
        checkContext();

        /**
         * Check RecyclerView.
         * */
        checkRecyclerView();

        /**
         * Check RecyclerViewHelperAdapter.
         * */
        checkAdapter();

        LinearLayoutManager manager = setLayoutManager(context, orientation, reverseLayout);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);

        if (enableSwipeToDelete) itemTouchHelperCallback.setEnableSwipeToDelete();

        if (enableDragAndDrop) itemTouchHelperCallback.setEnableDragAndDrop();

        if (enableSwipeToDelete || enableDragAndDrop) {
            ItemTouchHelper helper = new ItemTouchHelper(itemTouchHelperCallback);
            helper.attachToRecyclerView(recyclerView);
        }

        return this;
    }

    private void checkContext() {
        if (context == null) new RuntimeException("Context is being null, please call `setContext(Context)`.");
    }

    private void checkRecyclerView() {
        if (recyclerView == null) new RuntimeException("RecyclerView is being null, please call `setRecyclerView(RecyclerView)`.");
    }

    private void checkAdapter() {
        if (adapter == null) new RuntimeException("RecyclerViewHelperAdapter is being null, please call `setAdapter(RecyclerViewHelperAdapter)`.");
    }

    private LinearLayoutManager setLayoutManager(@NonNull Context context, int orientation, boolean reverseLayout) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL)
            new RuntimeException("Invalid orientation:" + orientation);

        LinearLayoutManager manager = new LinearLayoutManager(context, orientation, reverseLayout);
        return manager;
    }
}
