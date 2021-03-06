package ch.philopateer.mibody.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
