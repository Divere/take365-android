package org.take365.network.models.responses.feed

import org.take365.network.models.FeedItem

/**
 * Created by divere on 20/08/2017.
 */
data class FeedResult(
        var list: List<FeedItem>,
        var isEmpty: Boolean
)