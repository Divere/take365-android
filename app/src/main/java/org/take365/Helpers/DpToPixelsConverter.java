package org.take365.Helpers;

import org.take365.Take365App;

/**
 * Created by divere on 30/10/2016.
 */

public class DpToPixelsConverter {
    public static int toPixels(int dp) {
        final float scale = Take365App.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
