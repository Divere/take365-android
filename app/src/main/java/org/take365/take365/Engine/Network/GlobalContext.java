package org.take365.take365.Engine.Network;

import org.take365.take365.Engine.Network.Models.StoryListItemModel;

import java.util.List;

/**
 * Created by Ermakov-MAC on 22.02.16.
 */
public class GlobalContext {
    private static GlobalContext ourInstance = new GlobalContext();

    public List<StoryListItemModel> myStoriesList;

    public static GlobalContext getInstance() {
        return ourInstance;
    }

    private GlobalContext() {
    }
}
