package org.take365.Network.Models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryImageImagesModel(
        var id: Int,
        var title: String,
        var description: String?,
        var thumb: StoryImageThumbModel,
        var thumbLarge: StoryImageThumbModel,
        var image: StoryImageThumbModel,
        var imageLarge: StoryImageThumbModel,
        var date: String,
        var timestamp: Long,
        var likesCount: Long,
        var isLiked: Boolean
) : Serializable
