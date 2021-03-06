package org.take365.network.models

import java.io.Serializable

/**
 * Created by evgeniy on 08.02.16.
 */
data class StoryProgress(
        var dateStart: String,
        var dateEnd: String,
        var percentsComplete: Double,
        var isComplete: Boolean,
        var isOutdated: Boolean,
        var passedDays: Int,
        var totalImages: Int,
        var totalImagesTitle: String,
        var totalDays: Int,
        var delayDays: Int,
        var delayDaysTitle: String,
        var delayDaysMakeSense: Boolean
) : Serializable
