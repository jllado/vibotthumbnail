package com.vibot.thumbnail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

const val htmlFile = "thumbnail.html"

@Service
class ThumbnailBuilder @Autowired constructor(
        private val commandRunner: CommandRunner
) {

    fun build(html: String, id: String) {
        val htmlFile = File(htmlFile)
        htmlFile.writeText(html)
        buildThumbnail(htmlFile.path, id)
    }

    //wkhtmltoimage --width 640 file:///home/jllado/pruebas/slide.html html.png
    private fun buildThumbnail(url: String, id: String) {
        commandRunner.run(File("."), "wkhtmltoimage", "--width", "740", url, "$id.png")
    }
}