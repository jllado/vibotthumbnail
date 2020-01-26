package com.vibot.thumbnail

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.FileSystemUtils
import java.io.File
import javax.imageio.ImageIO


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ThumbnailApiIT {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `given title and image url should return thumbnail image`() {
        val title = "Barça, Sabotajes, explosivos y el asalto al Parlament: los planes de los CDR"
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"
        val post = "{\"title\": \"$title\",\"image\": \"$image\"}"
        val urlResponseResult = mvc.perform(post("/thumbnail").contentType(MediaType.APPLICATION_JSON_UTF8).content(post))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.url", `is`(notNullValue()))).andReturn()
        val urlResponse = ObjectMapper().registerModule(KotlinModule()).readValue(urlResponseResult.response.contentAsString, ThumbnailResponse::class.java)
        mvc.perform(get(urlResponse.url).contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(status().isOk)
        assertImageSize()
    }

    private fun assertImageSize() {
        val thumbnail = ImageIO.read(File(thumbnailFile))
        assertThat(thumbnail.width, `is`(740))
        assertThat(thumbnail.height, `is`(386))
    }

    @After
    fun tearDown() {
        deleteAllFiles()
    }

    private fun deleteAllFiles() {
        FileSystemUtils.deleteRecursively(File(thumbnailFile))
        FileSystemUtils.deleteRecursively(File(htmlFile))
    }
}