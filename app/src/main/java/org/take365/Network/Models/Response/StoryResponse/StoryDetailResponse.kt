package org.take365.Network.Models.Response.StoryResponse

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.StoryDetailsModel

data class StoryDetailResponse(
        var result: StoryDetailsModel?
) : BaseResponse()
