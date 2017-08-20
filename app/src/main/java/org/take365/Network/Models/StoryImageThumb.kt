package org.take365.Network.Models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryImageThumb(
        var height: Int,
        var url: String,
        var width: Int
) : Serializable
