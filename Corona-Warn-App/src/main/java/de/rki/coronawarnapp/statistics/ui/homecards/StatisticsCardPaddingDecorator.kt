package de.rki.coronawarnapp.statistics.ui.homecards

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class StatisticsCardPaddingDecorator(
    @DimenRes val startPadding: Int,
    @DimenRes val endPadding: Int = startPadding,
    @DimenRes val cardDistance: Int = startPadding
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == RecyclerView.NO_POSITION) return

        val resources = parent.context.resources

        val adapter = parent.adapter
        when (itemPosition) {
            0 -> {
                outRect.left = resources.getDimensionPixelSize(startPadding)
            }
            (adapter?.itemCount ?: Int.MAX_VALUE) - 1 -> {
                outRect.right = resources.getDimensionPixelSize(endPadding)
            }
            else -> {
                val distance = resources.getDimensionPixelSize(cardDistance)
                outRect.left = distance / 2
                outRect.right = distance / 2
            }
        }
    }
}
