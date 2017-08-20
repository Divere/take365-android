package org.take365.helpers

import org.take365.Take365App

/**
 * Created by divere on 30/10/2016.
 */

object DpToPixelsConverter {
    fun toPixels(dp: Int): Int {
        val scale = Take365App.getAppContext().resources.displayMetrics.density
        return (dp * scale).toInt()
    }
}
