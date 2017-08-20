package org.take365.Network.Models.Response.FeedResponse

/**
 * Created by divere on 20/08/2017.
 */
data class FeedResult(
        var list: List<FeedItem>,
        var isEmpty: Boolean
)