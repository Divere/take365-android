package org.take365.network.models.responses.story

import org.take365.network.models.responses.BaseResponse
import org.take365.network.models.StoryListItem

data class GetStoryListResponse(
        var result: List<StoryListItem>?
) : BaseResponse()
