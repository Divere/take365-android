package org.take365.Network.Models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryListItemModel(
        var authors: List<AuthorModel>,
        var id: Int,
        var progress: StoryProgressModel,
        var status: Int,
        var title: String? = null,
        var url: String? = null
) : Serializable
