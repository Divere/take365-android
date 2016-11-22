package org.take365.Network.Models.Response.LoginResponse;

import java.io.Serializable;

/**
 * Created by evgeniy on 08.02.16.
 */
public class LoginResult implements Serializable {
    public int id;
    public String token;
    public String tokenExpires;
    public String username;
}
