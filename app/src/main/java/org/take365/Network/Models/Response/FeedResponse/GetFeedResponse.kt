package org.take365.Network.Models.Response.FeedResponse

import org.take365.Network.Models.Response.BaseResponse

/**
 * Created by divere on 20/08/2017.
 */
data class GetFeedResponse(
        var result: FeedResult?
) : BaseResponse()