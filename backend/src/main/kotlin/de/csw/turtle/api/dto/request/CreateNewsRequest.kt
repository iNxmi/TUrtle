package de.csw.turtle.api.dto.request

import org.springframework.web.multipart.MultipartFile

data class CreateNewsRequest(
    val title: String,
    val markdown: String,
    val banner: MultipartFile? = null
)