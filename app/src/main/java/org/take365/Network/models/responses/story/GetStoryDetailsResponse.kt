package org.take365.Network.models.responses.story

import org.take365.Network.models.responses.BaseResponse
import org.take365.Network.models.StoryDetails

data class GetStoryDetailsResponse(
        var result: StoryDetails?
) : BaseResponse()
