package org.take365.Network.Models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class Author(
        var id: Int,
        var url: String?,
        var username: String?,
        var userpic: StoryImageThumb?,
        var userpicLarge: StoryImageThumb?
) : Serializable
