package org.take365.take365.Engine.Network.Models.Response.StoryResponse;

import org.take365.take365.Engine.Network.Models.AuthorModel;
import org.take365.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.take365.Engine.Network.Models.StoryModel;
import org.take365.take365.Engine.Network.Models.StoryProgressModel;

import java.util.List;

/**
 * Created by Ermakov-MAC on 12.02.16.
 */
public class StoryListResponse extends BaseResponse {
    public List<StoryModel> result;
}
