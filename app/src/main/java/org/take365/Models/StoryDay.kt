package org.take365.Models

import org.take365.Network.Models.StoryImageImagesModel

import java.io.Serializable

/**
 * Created by divere on 29/10/2016.
 */

class StoryDay : Serializable {
    lateinit var day: String
    var image: StoryImageImagesModel? = null
}
