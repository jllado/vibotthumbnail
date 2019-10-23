package com.vibot.thumbnail

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class HtmlBuilderIT {

    @Autowired
    private lateinit var builder: HtmlBuilder

    @Test
    fun `given title and image url should return thumbnail html`() {
        val title = "Sabotajes, explosivos y el asalto al Parlament: los planes de los CDR que investiga el juez"
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"

        val html = builder.build(ThumbnailRequest(title, image))

        assertThat(html, containsString(title))
        assertThat(html, containsString(image))
    }
}