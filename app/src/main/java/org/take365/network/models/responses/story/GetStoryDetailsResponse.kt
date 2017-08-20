package org.take365.network.models.responses.story

import org.take365.network.models.responses.BaseResponse
import org.take365.network.models.StoryDetails

data class GetStoryDetailsResponse(
        var result: StoryDetails?
) : BaseResponse()
