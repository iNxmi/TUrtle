package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.CreateNewsRequest
import de.csw.turtle.api.v1.dto.response.GetNewsResponse
import de.csw.turtle.api.v1.entity.NewsEntity
import de.csw.turtle.api.v1.repository.NewsRepository
import de.csw.turtle.api.v1.service.FileSystemService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RestController
@RequestMapping("/api/v1/news")
class NewsController(
    val repository: NewsRepository,
    val fileSystemService: FileSystemService
) {

    val directoryRoot = fileSystemService.path("news")
    val directoryBanner = directoryRoot.resolve("banner")
    val directoryMarkdown = directoryRoot.resolve("markdown")

    @GetMapping
    fun getPaginated(
        pageable: Pageable = PageRequest.of(0, 20)
    ): ResponseEntity<Page<GetNewsResponse>> {
        val page = repository.findAll(pageable)
            .map { GetNewsResponse(it) }

        return ResponseEntity.ok(page)
    }

    @PostMapping
    fun create(
        @RequestBody createNewsRequest: CreateNewsRequest
    ): ResponseEntity<GetNewsResponse> {
        val news = NewsEntity(createNewsRequest.title)

        if (createNewsRequest.banner != null) {
            val destination = directoryRoot.resolve("${news.id}.webp")
            createNewsRequest.banner.transferTo(destination)
        }

        repository.save(news)

        return ResponseEntity
            .created(URI.create("/api/v1/news/${news.id}"))
            .body(GetNewsResponse(news))
    }

}