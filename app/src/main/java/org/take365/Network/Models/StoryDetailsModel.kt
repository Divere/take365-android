package org.take365.Network.Models

/**
 * Created by Ermakov-MAC on 18.02.16.
 */
data class StoryDetailsModel(
        var authors: List<AuthorModel>,
        var id: Int,
        var images: List<StoryImageImagesModel>,
        var progress: StoryProgressModel,
        var status: Int,
        var title: String?,
        var url: String?
)
