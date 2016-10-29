package org.take365.take365.Engine.Network.Models;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class StoryProgressModel implements Serializable {
    public int delayDays;
    public int passedDays;
    public double percentsComplete;
    public int totalImages;
    public String totalImagesTitle;
}
