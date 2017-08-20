package org.take365.network.models.responses.login

import org.take365.network.models.ErrorModel

/**
 * Created by evgeniy on 08.02.16.
 */
data class LoginResponse(
        var result: LoginResult?,
        var errors: List<ErrorModel>?
)
