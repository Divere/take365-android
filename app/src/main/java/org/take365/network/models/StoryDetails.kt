package org.take365.network.models

import java.io.Serializable

/**
 * Created by Ermakov-MAC on 18.02.16.
 */
data class StoryDetails(
        var authors: List<Author>,
        var id: Int,
        var images: List<StoryImage>,
        var progress: StoryProgress,
        var status: Int,
        var title: String?,
        var url: String?
) : Serializable
