package com.vibot.thumbnail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileInputStream

@RestController
class ThumbnailApi @Autowired constructor(
        private val service: ThumbnailService
){

    @PostMapping("/thumbnail")
    fun buildThumbnail(@RequestBody request: ThumbnailRequest): ThumbnailResponse {
        return service.buildThumbnail(request)
    }

    @GetMapping(value = [thumbnailFile], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getThumbnail(): ResponseEntity<Resource> {
        val file = File(thumbnailFile)
        val resource = InputStreamResource(FileInputStream(file))
        return ResponseEntity.ok().body(resource)
    }
}