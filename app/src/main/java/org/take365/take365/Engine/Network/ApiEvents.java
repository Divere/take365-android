package org.take365.take365.Engine.Network;

import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResult;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryResult;
import org.take365.take365.Engine.Network.Models.StoryModel;

/**
 * Created by evgeniy on 08.02.16.
 */
public class ApiEvents {
    public void loginCompleted(LoginResult result, String error) {
    }

    public void getStoryResult(StoryResult result, String error) {
    }

    public void getStoryListResult(StoryListResponse result, String error) {
    }

    public void createStoryResult(StoryModel story, String error) {
    }

    public void uploadImageProgressChanged(float progress){
    }

    public void uploadImageCompleted(boolean success){
    }
}
