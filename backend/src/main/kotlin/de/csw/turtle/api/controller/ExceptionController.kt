package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.exception.exceptions.exception.ExceptionNotFoundException
import de.csw.turtle.api.repository.ExceptionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    private val exceptionRepository: ExceptionRepository
) {

    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page") pageNumber: Int = 0,
        @RequestParam(name = "size") pageSize: Int = 20,
        @RequestParam(name = "sort") sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction") direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetExceptionResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = exceptionRepository.findAll(pageRequest).map { GetExceptionResponse(it) }
        return ResponseEntity.ok(page)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetExceptionResponse> {
        val entity = exceptionRepository.findById(id).getOrNull()
            ?: throw ExceptionNotFoundException(id)

        return ResponseEntity.ok(GetExceptionResponse(entity))
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        val entity = exceptionRepository.findById(id).getOrNull()
            ?: throw ExceptionNotFoundException(id)

        exceptionRepository.delete(entity)

        return ResponseEntity.noContent().build()
    }

}