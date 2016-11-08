package org.take365.Network.Models;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class StoryListItemModel implements Serializable {
    public AuthorModel[] authors;
    public int id;
    public StoryProgressModel progress;
    public int status;
    public String title;
    public String utl;
}
