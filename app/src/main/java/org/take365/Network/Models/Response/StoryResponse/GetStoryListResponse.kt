package org.take365.Network.Models.Response.StoryResponse

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.StoryListItem

data class GetStoryListResponse(
        var result: List<StoryListItem>?
) : BaseResponse()
