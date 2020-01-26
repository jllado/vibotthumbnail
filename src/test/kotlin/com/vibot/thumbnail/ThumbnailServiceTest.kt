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
    private lateinit var thumbnailBuilder: ThumbnailBuilder

    @Mock
    private lateinit var htmlBuilder: HtmlBuilder

    @InjectMocks
    private lateinit var service: ThumbnailService

    @Test
    fun `should return thumbnail url`() {
        val request = ThumbnailRequest("any title", "http://anyimage.com")

        assertThat(service.buildThumbnail(request).url, `is`("/thumbnail.png"))
    }

    @Test
    fun `should build thumbnail with html generated`() {
        val request = ThumbnailRequest("any title", "http://anyimage.com")
        val html = "any html"
        doReturn(html).`when`(htmlBuilder).build(request)

        service.buildThumbnail(request)

        verify(thumbnailBuilder).build(html)
    }
}