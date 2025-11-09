package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.request.CreateNewsRequest
import de.csw.turtle.api.dto.response.GetNewsResponse
import de.csw.turtle.api.repository.NewsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/news")
class NewsController(
    val repository: NewsRepository
) {

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
       TODO()
    }

}