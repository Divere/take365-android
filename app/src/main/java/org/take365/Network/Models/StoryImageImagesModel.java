package org.take365.Network.Models;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class StoryImageImagesModel implements Serializable {
    public int id;
    public String title;
    public String description;
    public StoryImageThumbModel thumb;
    public StoryImageThumbModel thumbLarge;
    public StoryImageThumbModel image;
    public StoryImageThumbModel imageLarge;
    public String date;
    public long timestamp;
    public long likesCount;
    public boolean isLiked;
}
