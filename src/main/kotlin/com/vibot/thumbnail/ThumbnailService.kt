package com.vibot.thumbnail

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThumbnailService @Autowired constructor(
        private val idBuilder: IdBuilder,
        private val imageBuilder: ImageBuilder,
        private val htmlBuilder: HtmlBuilder
) {

    fun buildThumbnail(request: ThumbnailRequest): ThumbnailResponse {
        return buildImage("/thumbnail/", htmlBuilder.buildThumbnail(request))
    }

    fun buildImage(request: ThumbnailRequest): ThumbnailResponse {
        return buildImage("/image/", htmlBuilder.buildImage(request))
    }

    private fun buildImage(path: String, html: String): ThumbnailResponse {
        val id = idBuilder.build()
        imageBuilder.build(html, id)
        return ThumbnailResponse("$path$id")
    }
}

@Service
class IdBuilder {
    fun build(): String = RandomStringUtils.randomAlphabetic(8)
}
