package org.take365.Network.Models.Response.LoginResponse

import org.take365.Network.Models.ErrorModel

/**
 * Created by evgeniy on 08.02.16.
 */
data class LoginResponse(
        var result: LoginResult?,
        var errors: List<ErrorModel>?
)
