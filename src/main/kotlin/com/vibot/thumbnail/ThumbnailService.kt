package com.vibot.thumbnail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ThumbnailService @Autowired constructor(
        private val thumbnailBuilder: ThumbnailBuilder,
        private val htmlBuilder: HtmlBuilder
) {

    fun buildThumbnail(request: ThumbnailRequest): ThumbnailResponse {
        thumbnailBuilder.build(htmlBuilder.build(request))
        return ThumbnailResponse("/$thumbnailFile")
    }
}
