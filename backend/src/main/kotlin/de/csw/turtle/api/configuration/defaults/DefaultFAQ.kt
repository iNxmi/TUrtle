package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.FAQService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultFAQ(
    private val service: FAQService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(
            name = "Wie erstelle ich einen Account?",
            title = "Wie erstelle ich einen Account?",
            content = """
                > Ab Februar 2025 haben wir unser altes Buchungssystem "TorTUga" durch das neue "TUrtle" ersetzt. In TUrtle ist es Euch möglich eigenständig außerhalb von Sprechstunden einen Account zu erstellen. Wichtig ist nur, dass Ihr eine TU-ID besitzt. 
                > Zur Account-Erstellung müsst Ihr diesem Link folgen: {url}
            """.trimIndent(),
            enabled = true
        )

        service.create(
            name = "Wie leihe ich ein Gerät aus?",
            title = "Wie leihe ich ein Gerät aus?",
            content = """
                > Erstmal im Account registiert, können einfache Geräte oder Zubehör ganz leicht eigenständig vor Ort ausgeliehen werden.
                > Handelt es sich bei dem Gerät um ein besonderes z.B. Gaming Laptop oder iPad ist ein Leihvertrag notwendig. 
                > Das heißt Ihr müsstet in die Sprechstunde kommen und ein Admin leitet Euch durch den Ausleihprozess.
            """.trimIndent(),
            enabled = true
        )

        service.create(
            name = "Welche Geräte kann ich ausleihen?",
            title = "Welche Geräte kann ich ausleihen?",
            content = """
                > Eine vollständige Liste über die auszuleihbare Geräte findet Ihr wenn Ihr Euch im TUrtle-Portal anmeldet. 
                > Neben PCs mit MaxQDA stehen z.B. Beamer oder iPads zur Verfügung.
            """.trimIndent(),
            enabled = true
        )

        service.create(
            name = "Kann ich die CSW buchen?",
            title = "Kann ich die CSW buchen?",
            content = """
                > Jeder Dozierende oder Angestellte der TU, damit auch SHKs (mit CSW-Account) darf den Raum in den Randzeiten (vor 10:00 und nach 16:00) eigenständig buchen. 
                > Wenn es sich um eine Anfrage innerhalb unserer Hauptzeit handelt (10:00 - 16:00) kann eine Anfrage gestellt werden, diese muss jedoch erst durch die Admins genehmigt werden.
            """.trimIndent(),
            enabled = true
        )
    }

}