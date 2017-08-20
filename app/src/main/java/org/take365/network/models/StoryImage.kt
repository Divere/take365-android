package org.take365.network.models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryImage(
        var id: Int,
        var title: String,
        var description: String?,
        var thumb: StoryImageThumb,
        var thumbLarge: StoryImageThumb,
        var image: StoryImageThumb,
        var imageLarge: StoryImageThumb,
        var date: String,
        var timestamp: Long,
        var likesCount: Long,
        var isLiked: Boolean
) : Serializable
