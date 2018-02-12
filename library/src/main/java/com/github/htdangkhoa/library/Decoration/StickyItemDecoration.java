package com.github.htdangkhoa.library.Decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.htdangkhoa.library.Adapter.RecyclerViewHelperAdapter;

/**
 * Created by dangkhoa on 2/12/18.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    Context context;
    RecyclerViewHelperAdapter adapter;
    StickyItemListener stickyItemListener;

    public StickyItemDecoration(Context context, RecyclerViewHelperAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        this.stickyItemListener = (StickyItemListener) adapter;
    }

    private final Rect mTempRect = new Rect();
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);

        if (pos == RecyclerView.NO_POSITION) {
            return;
        }

        if (stickyItemListener.isSection(pos)) {
            View header = getHeaderView(parent, pos);

            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            initMargins(mTempRect, header);
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = header.getHeight() + mTempRect.top + mTempRect.bottom;
            } else {
                outRect.left = header.getWidth() + mTempRect.left + mTempRect.right;
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        final int childCount = parent.getChildCount();
        if (childCount <= 0 || adapter.getItemCount() <= 0) {
            return;
        }

        if (stickyItemListener.onCreateHeaderViewHolder(parent) == null) stickyItemListener.onCreateHeaderViewHolder(parent);
        RecyclerView.ViewHolder viewHolder = stickyItemListener.onCreateHeaderViewHolder(parent);
        fixLayoutSize(viewHolder.itemView, parent);

        String previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);
            if (position == RecyclerView.NO_POSITION) {
                continue;
            }
            stickyItemListener.onBindHeaderViewHolder(viewHolder, position);

            String title = String.valueOf(stickyItemListener.getSectionHeader(position));
            if (!previousHeader.equals(title)) {
                drawHeader(c, child, viewHolder.itemView);
                previousHeader = title;
            }
        }
    }

    private void fixLayoutSize(View headerView, RecyclerView parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), headerView.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), headerView.getLayoutParams().height);

        headerView.measure(childWidth, childHeight);

        headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
    }

    private void drawHeader(Canvas c, View child, View headerView) {
        c.save();
        c.translate(0, Math.max(0, child.getTop() - child.getMeasuredHeight() + 6));
        headerView.draw(c);
        c.restore();
    }

    private void initMargins(Rect margins, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            initMarginRect(margins, marginLayoutParams);
        } else {
            margins.set(0, 0, 0, 0);
        }
    }

    private void initMarginRect(Rect marginRect, ViewGroup.MarginLayoutParams marginLayoutParams) {
        marginRect.set(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }

    LongSparseArray<View> mHeaderViews = new LongSparseArray<>();
    private View getHeaderView(RecyclerView parent, int position) {
        RecyclerView.ViewHolder viewHolder = stickyItemListener.onCreateHeaderViewHolder(parent);
        stickyItemListener.onBindHeaderViewHolder(viewHolder, position);
        View itemView = viewHolder.itemView;

        if (itemView.getLayoutParams() == null) {
            itemView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int widthSpec;
        int heightSpec;
        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();

        if (orientation == LinearLayoutManager.VERTICAL) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
        }

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), itemView.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), itemView.getLayoutParams().height);
        itemView.measure(childWidth, childHeight);
        itemView.layout(0, 0, itemView.getMeasuredWidth(), itemView.getMeasuredHeight());
        mHeaderViews.put(stickyItemListener.getSectionHeader(position), itemView);

        return itemView;
    }
}
