package com.github.htdangkhoa.library.Decoration;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dangkhoa on 2/12/18.
 */

public interface StickyItemListener {
//    CharSequence getSectionHeader(int position);
//    void onBindHeaderView(View headerView, int position);
//    boolean isSection(int position);
    long getHeaderId(int position);
}
