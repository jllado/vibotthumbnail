package com.vibot.thumbnail

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Test
import org.springframework.util.FileSystemUtils
import java.io.File

class ThumbnailBuilderIT {

    private val thumbnail = File(thumbnailFile)
    private val html = File(htmlFile)

    private val builder = ThumbnailBuilder(CommandRunner())

    @Test
    fun `should build video`() {
        builder.build(html())

        assertThat(thumbnail.exists(),`is`(true))
        assertThat(thumbnail.length(), `is`(greaterThan(0L)))
    }

    private fun html(): String {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <img src=\"http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg\" class=\"thumbnail\" />\n" +
                "    </div>\n" +
                "    <div >\n" +
                "        <h1 class=\"title\" >Sabotajes, explosivos y el asalto al Parlament: los planes de los CDR que investiga el juez</h1>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>"
    }

    @After
    fun tearDown() {
        //deleteAllFiles()
    }

    private fun deleteAllFiles() {
        FileSystemUtils.deleteRecursively(thumbnail)
        FileSystemUtils.deleteRecursively(html)
    }
}