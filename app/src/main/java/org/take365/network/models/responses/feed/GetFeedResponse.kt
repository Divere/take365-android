package org.take365.network.models.responses.feed

import org.take365.network.models.responses.BaseResponse

/**
 * Created by divere on 20/08/2017.
 */
data class GetFeedResponse(
        var result: FeedResult?
) : BaseResponse()