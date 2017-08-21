package ch.philopateer.mibody.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by mamdouhelnakeeb on 12/17/16.
 */

public class CenterItemLayoutManager extends LinearLayoutManager {
    private final int parentWidth;
    private final int itemWidth;
    private final int numItems;

    public CenterItemLayoutManager(final Context context, final int orientation, final int parentWidth, final int itemWidth, final int numItems) {
        super(context, orientation, false);
        this.parentWidth = parentWidth;
        this.itemWidth = itemWidth;
        this.numItems = numItems;
    }

    @Override
    public int getPaddingLeft() {
        final int totalItemWidth = itemWidth * numItems;
        if (totalItemWidth >= parentWidth) {
            return super.getPaddingLeft(); // do nothing
        } else {
            return Math.round(parentWidth / 2f - totalItemWidth / 2f);
        }
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}