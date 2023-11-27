package mhha.sample.besttastehoues

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    @field:Json(name="item") val items: List<SearchItem>
)

data class SearchItem(
    @field:Json(name="title") val title: String,
    @field:Json(name="link") val link: String,
    @field:Json(name="category") val category: String,
    @field:Json(name="roadAddress") val roadAddress: String,
    @field:Json(name="mapx") val mapx: Int,
    @field:Json(name="mapy") val mapy: Int,
)
