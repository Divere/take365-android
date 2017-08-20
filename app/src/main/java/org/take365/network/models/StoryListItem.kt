package org.take365.network.models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryListItem(
        var authors: List<Author>,
        var id: Int,
        var progress: StoryProgress,
        var status: Int,
        var title: String?,
        var url: String?
) : Serializable
