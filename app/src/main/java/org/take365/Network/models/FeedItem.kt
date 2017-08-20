package org.take365.Network.models

import org.take365.Network.models.StoryImageThumb
import org.take365.Network.models.StoryListItem

/**
 * Created by divere on 20/08/2017.
 */
data class FeedItem(
        var id: Int,
        var title: String,
        var description: String?,
        var thumb: StoryImageThumb,
        var thumbLarge: StoryImageThumb,
        var story: StoryListItem,
        var date: String,
        var timestamp: Long,
        var likesCount: Long,
        var isLiked: Boolean
)