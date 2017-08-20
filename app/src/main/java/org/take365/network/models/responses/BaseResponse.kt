package org.take365.network.models.responses

import org.take365.network.models.ErrorModel

/**
 * Created by evgeniy on 08.02.16.
 */
open class BaseResponse {
    var errors: List<ErrorModel>? = null
}
