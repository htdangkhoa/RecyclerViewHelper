package com.github.htdangkhoa.library.Decoration;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by dangkhoa on 2/12/18.
 */

public interface StickyItemListener<VH extends RecyclerView.ViewHolder> {
//    CharSequence getSectionHeader(int position);
//    void onBindHeaderView(View headerView, int position);
//    boolean isSection(int position);
//    long getHeaderId(int position);
    VH onCreateHeaderViewHolder(ViewGroup parent);
    void onBindHeaderViewHolder(VH holder, int position);
    char getSectionHeader(int position);
    boolean isSection(int position);
}
