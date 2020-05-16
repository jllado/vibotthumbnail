package com.vibot.thumbnail

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class HtmlBuilderIT {

    @Autowired
    private lateinit var builder: HtmlBuilder

    @Test
    fun `given title and image url should return utf-8 740x360 thumbnail html`() {
        val title = "Sabotajes, explosivos y el asalto al Parlament: los planes de los CDR que investiga el juez"
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"

        val html = builder.buildThumbnail(ThumbnailRequest(title, image))

        assertThat(html, containsString(title))
        assertThat(html, containsString(image))
        assertSize(html)
        assertUtf8(html)
    }

    @Test
    fun `given only image url should return utf-8 740x360 image html`() {
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"

        val html = builder.buildImage(ThumbnailRequest(image))

        assertThat(html, not(containsString("<h1>")))
        assertThat(html, containsString(image))
        assertSize(html)
        assertUtf8(html)
    }

    private fun assertUtf8(html: String) {
        assertThat(html, containsString("<meta charset=\"UTF-8\" />"))
    }

    private fun assertSize(html: String) {
        assertThat(html, containsString("width: 740px;"))
        assertThat(html, containsString("height: 386px;"))
    }
}