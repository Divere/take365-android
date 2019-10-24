package org.take365.components

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import org.take365.views.StorySectionView

/**
 * Created by divere on 30/10/2016.
 */

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //outRect.left = space;
        //outRect.right = space;
        outRect.bottom = space

        if(view is StorySectionView) {
            outRect.left = space * 2
        }

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}