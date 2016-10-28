package org.take365.take365.Engine.Network.Models.Request;

/**
 * Created by divere on 28/10/2016.
 */

public class LoginWithTokenRequest {
    public String accessToken;

    public LoginWithTokenRequest(String accessToken)
    {
        this.accessToken = accessToken;
    }
}
