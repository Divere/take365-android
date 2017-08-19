package org.take365.Network.Models.Response.LoginResponse

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class LoginResult(
        var id: Int,
        var token: String,
        var tokenExpires: String?,
        var username: String
) : Serializable
