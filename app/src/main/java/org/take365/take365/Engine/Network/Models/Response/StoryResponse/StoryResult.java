package org.take365.take365.Engine.Network.Models.Response.StoryResponse;

import org.take365.take365.Engine.Network.Models.AuthorModel;
import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.Engine.Network.Models.StoryProgressModel;

/**
 * Created by evgeniy on 08.02.16.
 */
public class StoryResult {
    public AuthorModel[] authors;
    public int id;
    public StoryImageImagesModel[] images;
    public StoryProgressModel progress;
    public int status;
    public String title;
    public String url;
}
