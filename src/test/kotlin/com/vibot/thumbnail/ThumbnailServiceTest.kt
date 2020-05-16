package com.vibot.thumbnail

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ThumbnailServiceTest {

    @Mock
    private lateinit var idBuilder: IdBuilder
    @Mock
    private lateinit var imageBuilder: ImageBuilder
    @Mock
    private lateinit var htmlBuilder: HtmlBuilder

    @InjectMocks
    private lateinit var service: ThumbnailService

    @Test
    fun `should return video thumbnail url`() {
        val id = "anyid"
        doReturn(id).`when`(idBuilder).build()

        val request = ThumbnailRequest("http://anyimage.com", "any title")

        assertThat(service.buildThumbnail(request).url, `is`("/thumbnail/$id"))
    }

    @Test
    fun `should build thumbnail with html generated`() {
        val id = "anyid"
        doReturn(id).`when`(idBuilder).build()
        val request = ThumbnailRequest("http://anyimage.com", "any title")
        val html = "any html"
        doReturn(html).`when`(htmlBuilder).buildThumbnail(request)

        service.buildThumbnail(request)

        verify(imageBuilder).build(html, id)
    }

    @Test
    fun `given only image should return video image url`() {
        val id = "anyid"
        doReturn(id).`when`(idBuilder).build()

        val request = ThumbnailRequest("http://anyimage.com")

        assertThat(service.buildImage(request).url, `is`("/image/$id"))
    }

    @Test
    fun `given only image should build image with html generated`() {
        val id = "anyid"
        doReturn(id).`when`(idBuilder).build()
        val request = ThumbnailRequest("http://anyimage.com")
        val html = "any html"
        doReturn(html).`when`(htmlBuilder).buildImage(request)

        service.buildImage(request)

        verify(imageBuilder).build(html, id)
    }
}