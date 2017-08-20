package org.take365.Network.models.responses.story

import org.take365.Network.models.responses.BaseResponse
import org.take365.Network.models.StoryListItem

data class GetStoryListResponse(
        var result: List<StoryListItem>?
) : BaseResponse()
