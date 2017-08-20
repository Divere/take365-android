package org.take365.Network.Models.Response.StoryResponse

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.StoryDetails

data class GetStoryDetailsResponse(
        var result: StoryDetails?
) : BaseResponse()
