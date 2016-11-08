package org.take365.Network.Models;

import java.util.List;

/**
 * Created by Ermakov-MAC on 18.02.16.
 */
public class StoryDetailsModel {
    public List<AuthorModel> authors;
    public int id;
    public List<StoryImageImagesModel> images;
    public StoryProgressModel progress;
    public int status;
    public String title;
    public String url;
}
