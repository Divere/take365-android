package org.take365.Network.Models.Response.StoryResponse

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.StoryListItemModel

data class StoryListResponse(
        var result: List<StoryListItemModel>?
) : BaseResponse()
