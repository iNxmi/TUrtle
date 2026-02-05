package de.csw.turtle.api

enum class Settings(val key:String) {
    GENERAL_FQDN("general.fqdn"),

    EMOJIS_ALL("emojis.all"),
    EMOJIS_SIZE("emojis.size"),
    EMOJIS_MAX_RETRIES("emojis.max-retries"),

    USER_VERIFICATION_DURATION("user.verification.duration"),

    ALTCHA_SECRET("altcha.secret"),
    ALTCHA_MAX_NUMBER("altcha.max-number"),
    ALTCHA_DURATION("altcha.duration"),

    DOOR_OPEN_DURATION("door.open.duration"),
    DOOR_SCHEDULE_START("door.schedule.start"),
    DOOR_SCHEDULE_END("door.schedule.end"),
    DOOR_SSH_COMMAND("door.ssh.command"),
    DOOR_SSH_HOSTNAME("door.ssh.hostname"),
    DOOR_SSH_PORT("door.ssh.port"),
    DOOR_SSH_USERNAME("door.ssh.username"),
    DOOR_SSH_PASSWORD("door.ssh.password"),

    LOCKER_SCHEDULE_START("locker.schedule.start"),
    LOCKER_SCHEDULE_END("locker.schedule.end"),
    LOCKER_SSH_COMMAND("locker.ssh.command"),
    LOCKER_SSH_HOSTNAME("locker.ssh.hostname"),
    LOCKER_SSH_PORT("locker.ssh.port"),
    LOCKER_SSH_USERNAME("locker.ssh.username"),
    LOCKER_SSH_PASSWORD("locker.ssh.password"),

    JWT_SECRET("jwt.secret"),
    JWT_DURATION_ACCESS("jwt.duration.access"),
    JWT_DURATION_REFRESH("jwt.duration.refresh"),

    CONTENT_TEMPLATE_IMPRINT("content.template.imprint"),
    CONTENT_TEMPLATE_GDPR("content.template.gdpr"),
    CONTENT_TEMPLATE_TOS("content.template.tos"),
    CONTENT_TEMPLATE_CONTACT("content.template.contact"),
    CONTENT_TEMPLATE_ABOUT("content.template.about"),

    EMAIL_TEMPLATE_VERIFY("email.template.verify"),
}