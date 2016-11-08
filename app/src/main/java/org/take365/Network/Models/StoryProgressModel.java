package org.take365.Network.Models;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class StoryProgressModel implements Serializable {
    public String dateStart;
    public String dateEnd;
    public double percentsComplete;
    public boolean isComplete;
    public boolean isOutdated;
    public int passedDays;
    public int totalImages;
    public String totalImagesTitle;
    public int totalDays;
    public int delayDays;
    public String delayDaysTitle;
    public boolean delayDaysMakeSense;
}
