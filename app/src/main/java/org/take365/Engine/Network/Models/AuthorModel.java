package org.take365.Engine.Network.Models;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class AuthorModel implements Serializable {
    public int id;
    public String url;
    public String username;
    public StoryImageThumbModel userpic;
    public StoryImageThumbModel userpicLarge;
}
