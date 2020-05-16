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
    fun `given title and image url should return video thumbnail image`() {
        val title = "CORONAVIRUS"
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"
        val post = "{\"text\": \"$title\",\"image\": \"$image\"}"
        val urlResponseResult = mvc.perform(post("/thumbnail").contentType(MediaType.APPLICATION_JSON_UTF8).content(post))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.url", `is`(notNullValue()))).andReturn()
        val urlResponse = ObjectMapper().registerModule(KotlinModule()).readValue(urlResponseResult.response.contentAsString, ThumbnailResponse::class.java)
        mvc.perform(get("${urlResponse.url}").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(status().isOk)
        assertImageSize(urlResponse.url)
    }

    @Test
    fun `given image url should return video image`() {
        val image = "http://newnation.sg/wp-content/uploads/random-pic-internet-22.jpg"
        val post = "{\"image\": \"$image\"}"
        val urlResponseResult = mvc.perform(post("/image").contentType(MediaType.APPLICATION_JSON_UTF8).content(post))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.url", `is`(notNullValue()))).andReturn()
        val urlResponse = ObjectMapper().registerModule(KotlinModule()).readValue(urlResponseResult.response.contentAsString, ThumbnailResponse::class.java)
        mvc.perform(get("${urlResponse.url}").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(status().isOk)
        assertImageSize(urlResponse.url)
    }

    private fun assertImageSize(url: String) {
        val imageId = url.split("/").last()
        val imageFile = "$imageId.png"
        val image = ImageIO.read(File(imageFile))
        assertThat(image.width, `is`(740))
        assertThat(image.height, `is`(386))
        FileSystemUtils.deleteRecursively(File(imageFile))
        FileSystemUtils.deleteRecursively(File("$imageId.html"))
    }
}