package de.csw.turtle.api.service

import com.samskivert.mustache.Mustache
import org.springframework.stereotype.Service

@Service
class MustacheService {

    fun getInserted(template: String, variables: Map<String, Any?>): String {
        val compiled = Mustache.compiler().compile(template)
        return compiled.execute(variables)
    }

}