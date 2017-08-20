package org.take365.Network.models.responses.feed

import org.take365.Network.models.responses.BaseResponse

/**
 * Created by divere on 20/08/2017.
 */
data class GetFeedResponse(
        var result: FeedResult?
) : BaseResponse()