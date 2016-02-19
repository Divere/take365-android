package org.take365.take365.Engine.Network;

import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResult;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.Engine.Network.Models.StoryListItemModel;

/**
 * Created by evgeniy on 08.02.16.
 */
public class ApiEvents {
    public void loginCompleted(LoginResult result, String error) {
    }

    public void getStoryResult(StoryDetailsModel result, String error) {
    }

    public void getStoryListResult(StoryListResponse result, String error) {
    }

    public void createStoryResult(StoryListItemModel story, String error) {
    }

    public void uploadImageProgressChanged(float progress){
    }

    public void uploadImageCompleted(boolean success){
    }
}
