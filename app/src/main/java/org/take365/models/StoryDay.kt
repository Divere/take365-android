package org.take365.models

import org.take365.Network.models.StoryImage

import java.io.Serializable

/**
 * Created by divere on 29/10/2016.
 */

class StoryDay : Serializable {
    lateinit var day: String
    var image: StoryImage? = null
}
