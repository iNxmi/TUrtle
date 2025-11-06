package de.csw.turtle.api.v1.service

import org.springframework.stereotype.Component
import java.nio.file.Path
import java.nio.file.Paths

@Component
class FileSystemService {

    val directory = Paths.get("/var/lib/turtle/backend")

    fun file(path: String) = file(Paths.get(path))
    fun file(path: Path) = directory.resolve(path).toFile()

    fun path(path: String) = path(Paths.get(path))
    fun path(path: Path) = directory.resolve(path)


}