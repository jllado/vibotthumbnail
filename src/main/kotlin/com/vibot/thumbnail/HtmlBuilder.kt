package com.vibot.thumbnail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.*

@Service
class HtmlBuilder @Autowired constructor(
        private val templateEngine: TemplateEngine
) {

    fun build(request: ThumbnailRequest): String {
        val context = Context()
        context.setVariable("text", request.text)
        context.setVariable("thumbnail", request.image)
        context.setVariable("trendingNewsImage", Base64.getEncoder().encodeToString(HtmlBuilder::class.java.getResourceAsStream("/static/trending_news_mark.png").readAllBytes()))
        return templateEngine.process("thumbnail", context)
    }
}
