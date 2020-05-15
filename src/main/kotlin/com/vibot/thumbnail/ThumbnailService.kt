package com.vibot.thumbnail

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThumbnailService @Autowired constructor(
        private val idBuilder: IdBuilder,
        private val thumbnailBuilder: ThumbnailBuilder,
        private val htmlBuilder: HtmlBuilder
) {

    fun buildThumbnail(request: ThumbnailRequest): ThumbnailResponse {
        val id = idBuilder.build()
        thumbnailBuilder.build(htmlBuilder.build(request), id)
        return ThumbnailResponse("/thumbnail/$id")
    }
}


@Service
class IdBuilder {
    fun build(): String = RandomStringUtils.randomAlphabetic(8)
}
