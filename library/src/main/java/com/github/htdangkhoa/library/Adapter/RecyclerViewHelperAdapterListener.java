package com.github.htdangkhoa.library.Adapter;

/**
 * Created by dangkhoa on 2/11/18.
 */

public interface RecyclerViewHelperAdapterListener {
    void onItemMove(int fromPosition, int toPosition);
    void onItemRemove(int position);
}
