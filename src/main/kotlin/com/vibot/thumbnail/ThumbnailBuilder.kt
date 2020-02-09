package com.vibot.thumbnail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

const val thumbnailFile = "thumbnail.png"
const val htmlFile = "thumbnail.html"

@Service
class ThumbnailBuilder @Autowired constructor(
        private val commandRunner: CommandRunner
) {

    fun build(html: String) {
        val htmlFile = File(htmlFile)
        htmlFile.writeText(html)
        buildThumbnail(htmlFile.path)
    }

    //wkhtmltoimage --width 640 file:///home/jllado/pruebas/slide.html html.png
    private fun buildThumbnail(url: String) {
        commandRunner.run(File("."), "wkhtmltoimage", "--width", "740", url, thumbnailFile)
    }
}