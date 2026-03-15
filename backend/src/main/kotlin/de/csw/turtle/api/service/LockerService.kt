package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.LockerRepository
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import kotlin.jvm.optionals.getOrNull

@Service
class LockerService(
    override val repository: LockerRepository,
    private val configurationService: ConfigurationService,
    private val thymeleafService: ThymeleafService,
    private val sshService: SSHService
) : CRUDService<LockerEntity>() {

    private val maxNameLength = 64

    fun getByIndex(index: Int) =
        repository.findByIndex(index) ?: throw HttpException.NotFound("Locker with index '$index' not found.")

    fun getByName(name: String) =
        repository.findByName(name) ?: throw HttpException.NotFound("Locker with name '$name' not found.")

    @Transactional
    fun create(
        name: String,
        index: Int,
        isSoftwareUnlockable: Boolean,
        locked: Boolean
    ): LockerEntity {
        if (name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")

        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Locker with name '$name' already exists.")

        if (repository.findByIndex(index) != null)
            throw HttpException.Conflict("Locker with index '$index' already exists.")

        val entity = LockerEntity(
            name = name,
            index = index,
            isSoftwareUnlockable = isSoftwareUnlockable,
            locked = locked
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        index: Int? = null,
        isSoftwareUnlockable: Boolean? = null,
        locked: Boolean? = null
    ): LockerEntity {
        val entity = repository.findById(id).getOrNull()
            ?: throw HttpException.NotFound("Locker with id '$id' not found.")

        name?.let {
            if (it.isBlank() || it.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")

            if (it != entity.name && repository.findByName(it) != null)
                throw HttpException.Conflict("Locker with name '$it' already exists.")

            entity.name = it
        }

        index?.let {
            if (it != entity.index && repository.findByIndex(it) != null)
                throw HttpException.Conflict("Locker with index '$it' already exists.")

            entity.index = it
        }

        isSoftwareUnlockable?.let { entity.isSoftwareUnlockable = it }
        locked?.let { entity.locked = it }

        return repository.save(entity)
    }

    @Transactional
    fun unlock(id: Long) {
        val entity = getById(id) ?: throw HttpException.NotFound("Locker with id '$id' not found.")

        val isDebug = configurationService.getTyped<Boolean>(ConfigurationEntity.Key.DOOR_DEBUG_ENABLED)
        if (isDebug)
            return

        val context = Context().apply { setVariable("index", entity.index) }
        val template = configurationService.getTyped<String>(ConfigurationEntity.Key.LOCKER_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = configurationService.getTyped<String>(ConfigurationEntity.Key.LOCKER_SSH_HOSTNAME)
        val port = configurationService.getTyped<Int>(ConfigurationEntity.Key.LOCKER_SSH_PORT)
        val username = configurationService.getTyped<String>(ConfigurationEntity.Key.LOCKER_SSH_USERNAME)
        val password = configurationService.getTyped<String>(ConfigurationEntity.Key.LOCKER_SSH_PASSWORD)

        ssh(hostname, port, username, password, command)
    }

    @Async
    fun ssh(hostname: String, port: Int, username: String, password: String, command: String) =
        sshService.execute(hostname, port, username, password, command)

}