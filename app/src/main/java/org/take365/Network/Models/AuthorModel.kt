package org.take365.Network.Models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class AuthorModel(
        var id: Int,
        var url: String?,
        var username: String?,
        var userpic: StoryImageThumbModel?,
        var userpicLarge: StoryImageThumbModel?
) : Serializable
