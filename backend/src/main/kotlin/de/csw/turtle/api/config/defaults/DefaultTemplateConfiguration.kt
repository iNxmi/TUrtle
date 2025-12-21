package de.csw.turtle.api.config.defaults

import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.service.TemplateService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultTemplateConfiguration(
    private val service: TemplateService
) : CommandLineRunner {

    private val imprint = """
        ComputerStudienWerkstatt (CSW)
        Fachbereich Humanwissenschaften
        S1|13 Raum 218
        Alexanderstraße 6
        64283 Darmstadt
        Technische Universität Darmstadt
        Rechtsform
        Die Technische Universität Darmstadt ist eine rechtsfähige Körperschaft des öffentlichen Rechts gemäß § 1 Abs. 1 i.V.m. § 2 Abs. 1 Nr. 1 HHG (Hessisches Hochschulgesetz vom 14. Dezember 2009, GVBl. I S. 666). Seit dem In-Kraft-Treten des TU Darmstadt-Gesetzes (Gesetz zur organisatorischen Fortentwicklung der Technischen Universität Darmstadt vom 05. Dezember 2004, GVBl. I S. 382, in der Fassung vom 14. Dezember 2009, GVBl. I S. 699) ist sie autonome Universität des Landes Hessen.
        Vertreten durch
        Prof. Dr. Petra Grell, Professur für Allgemeine Pädagogik mit dem Schwerpunkt Medienpädagogik
        Fachbereich Humanwissenschaften
        S1|13 Raum 211
        Alexanderstraße 6
        64283 Darmstadt
        Technische Universität Darmstadt
        E-Mail: petra.grell@tu-darmstadt.de
        Haftungsausschluss
        Die ComputerStudienWerkstatt übernimmt keine Gewähr für die Aktualität, Richtigkeit und Vollständigkeit der bereitgestellten Informationen. Haftungsansprüche, die durch die Nutzung oder Nichtnutzung der dargebotenen Inhalte entstehen, sind ausgeschlossen, sofern kein vorsätzliches oder grob fahrlässiges Verschulden vorliegt.
        Urheberrecht
        Alle Inhalte dieser Seite unterliegen dem deutschen Urheberrecht. Eine Weiterverarbeitung oder Vervielfältigung bedarf der ausdrücklichen Zustimmung der Rechteinhabende.
    """.trimIndent()

    private val contact = """
        Haben Sie Fragen zur ComputerStudienWerkstatt (CSW)?
        Wir unterstützen Sie gerne bei allen Anliegen rund um Raumbuchungen, Geräteausleihe, Workshops oder allgemeine Anfragen.
        ComputerStudienWerkstatt (CSW)
        Fachbereich Humanwissenschaften
        S1|13 Raum 218
        Alexanderstraße 6
        64283 Darmstadt
        Technische Universität Darmstadt
        E-Mail: cswtud@gmail.com
        Wir freuen uns auf Ihre Nachricht!
    """.trimIndent()

    private val agb = """
        Voraussetzung und Bedingung für den Zugang zur ComputerStudienWerkstatt und zur Nutzung ihrer Ressourcen ist die Anerkennung und Einhaltung der Hausordnung der TU Darmstadt sowie der Allgemeinen Benutzerordnung für die Informationsverarbeitungs- und Kommunikations-Infrastruktur der TU Darmstadt (online einsehbar).
        Die Zugangsberechtigung umfasst:
        - den Zutritt in die ComputerStudienWerkstatt durch das elektronische Zugangssystem,
        - die Möglichkeit, ausleihbare Medien über den eigenen CSW-Account zu reservieren,
        - die Möglichkeit, digitale Medien über das elektronische Verwaltungssystem auszuleihen und innerhalb der CSW zu nutzen (Ausnahmen sind im System gekennzeichnet),
        - die Nutzung der frei zugänglichen Ressourcen innerhalb der CSW sowie
        - die Möglichkeit, als Lehrender Raumanfragen zur Reservierung der CSW außerhalb von 10-18 Uhr zu stellen.
        Die für die Accounterstellung benötigen personenbezogen Daten werden von uns vertraulich und nach den Anforderungen der Datenschutzgrundverordnung (DSGVO) behandelt. Die Speicherung der personenbezogenen Daten dient ausschließlich der Verwaltung der CSW, ist lediglich dem Betreuungsteam der CSW zugänglich und wird nicht an Dritte weitergegeben.
        Als Nutzende sind Sie aufgefordert, beim Umgang mit dem Inventar der ComputerStudienWerkstatt eine angemessene Sorgfalt walten zu lassen. Bei mutwilliger oder fahrlässiger Beschädigung von Mobiliar, Hard- oder Software wird die verursachende Person in die Verantwortung genommen.
        Aus Sicherheitsgründen ist es zu vermeiden, die ComputerStudienWerkstatt unverschlossen zu lassen. Das Zugangssystem dient der Sicherung des Bestandes, sein Umgehen ist nicht gestattet. Die Eingangstür ist eine Brandschutztür und darf daher nicht durch Gegenstände (wie bspw. den Schirmständer) offengehalten werden. Falls Sie Dritten den Zugang zur ComputerStudienWerkstatt ermöglichen, sind Sie für die Raumnutzung der entsprechenden Person verantwortlich.
        Zur Sicherung des Buch- und Gerätebestandes der ComputerStudienWerkstatt ist Weitergabe des eigenen Accounts an Dritte untersagt. Ferner wird der Bereich der Medienausleihe und die Bücherwand der CSW per Videokamera überwacht. Mit Betreten des Raums erklären die Nutzenden ihre Zustimmung zur Aufzeichnung. Im Regelfall werden die Aufzeichnungen gelöscht und nicht an Dritte weitergegeben. Im Falle eines Diebstahls behält sich das Institut für Allgemeine Pädagogik und Berufspädagogik die Prüfung der Aufzeichnungen und im Einzelfall die Weitergabe an die Polizei vor.
        Das Institut für Allgemeine Pädagogik und Berufspädagogik behält sich ausdrücklich das Recht vor, im Rahmen der in der Benutzungsordnung der TUDa vorgesehenen Maßnahmen entsprechende Überprüfungen durchzuführen. Verstöße gegen die Benutzungsordnung führen zum Ausschluss von der Zugangs- und Nutzungsberechtigung. Entzug ohne Gründe möglich.
    """.trimIndent()

    private val about = """
        Die ComputerStudienWerkstatt (CSW) der Technischen Universität Darmstadt ist ein interdisziplinär entwickelter Lern- und Erfahrungsraum, der im Wintersemester 1999/2000 aus einer Kooperation der Pädagogik, Informatik und Architektur hervorging (Diéz Aguilar & Sesink, 2000). Ihr konzeptioneller Ausgangspunkt verbindet die Gestaltung digitaler Technologien mit pädagogischen Fragestellungen und orientiert sich dabei an einer kritischen Bildungstheorie sowie aktuellen gesellschaftlichen Entwicklungen wie Digitalisierung und Mediatisierung (Sesink & Zentgraf, 2009).
        Zentral für das Verständnis der CSW ist Sesinks Konzept der „zurückhaltenden Technik“, das darauf abzielt, Technik so in Bildungsräume einzubetten, dass Lernende selbstbestimmt, kreativ und reflexiv mit digitalen Medien umgehen können, ohne durch diese determiniert zu werden (Sesink, 2006). Dies ermöglicht einen pädagogischen Möglichkeitsraum, in dem Studierende in einem projektorientierten und explorativen Rahmen eigene Ideen zum Einsatz digitaler Medien erproben und weiterentwickeln können.
        Im hochschuldidaktischen Kontext verfolgt die CSW das Ziel, Lernprozesse mit und über digitale Medien anzuregen und gleichzeitig digitale Kompetenzen im Sinne einer zeitgemäßen critical media literacy zu fördern. Durch die Kombination aus digitaler Infrastruktur, ästhetisch gestalteter Lernumgebung sowie Beratungs- und Workshopangeboten stellt die CSW einen prototypischen Ansatz für moderne, digital gestützte Lernwerkstätten dar.
        Die CSW versucht so eine Kultur der (gesellschaftlichen) Verantwortung in Verbindung mit einer Kultur des mit- und voneinander Lernens und Lehrens zu verbinden, um durch Kooperation und Partizipation die Möglichkeit zur innovativen und ganzheitlichen Verknüpfung von Wissen bei Lernende zu ermöglichen (Bandtel et al., 2022).
        Literatur
        Bandtel, M., Patrick Bergmann, & Eichenauer, U. (2022). Zukunftsbild Hochschullehre 2025. Hochschulforum Digitalisierung.
        Diéz Aguilar, Michael und Sesink, Werner (2000): Multimediale Lernumgebungen als Räume für Bildung: das Konzept der Computer-Studienwerkstatt. In: Sesink, Werner (Hrsg.): Bildung ans Netz. Implementierung Neuer Technologien in Bildungseinrichtungen – pädagogische und technische Vermittlungsaufgaben. S. 197–205. Wiesbaden: Hessisches Ministerium für Wirtschaft, Verkehr und Landesentwicklung.
        Ganguin, S., Gemkow, J., & Haubold, R. (2020). Medienkritik zwischen Medienkompetenz und Media Literacy: Medien- und subjektspezifische Einflüsse auf die medienkritische Decodierungsfähigkeit. MedienPädagogik: Zeitschrift für Theorie und Praxis der Medienbildung, 37, 51–66.
        Sesink, Werner (2006): Subjekt – Raum – Technik. Beiträge zur Theorie und Gestaltung neuer Medien in der Bildung. Münster und Berlin: LIT.
        Sesink, Werner und Zentgraf, Claudia (2009): Die ComputerStudienWerkstatt eine Lern- und Arbeitsumgebung mit Atmosphäre. Darmstadt: Technische Universität Darmstadt.
    """.trimIndent()

    private val datenschutzverordnung = """
        **Informationen nach Artikel 13 der Datenschutzgrundverordnung (DSGVO)**

        1. Verantwortlicher der Datenverarbeitung
        Technische Universität Darmstadt
        Karolinenplatz 5
        64289 Darmstadt
        Telefon: 06151 16 27063
        E-Mail: datenschutz@tu-darmstadt.de
        [https://www.tu-darmstadt.de](https://www.tu-darmstadt.de "https://www.tu-darmstadt.de")

        2. Ihre Rechte
        Sie haben uns gegenüber folgende Rechte hinsichtlich der Sie betreffenden personenbezogenen Daten:
        - Recht auf Auskunft,
        - Recht auf Berichtigung oder Löschung,
        - Recht auf Einschränkung der Verarbeitung,
        - Recht auf Widerspruch gegen die Verarbeitung,
        - Recht auf Datenübertragbarkeit.

        Sie haben zudem das Recht, sich bei einer Datenschutz-Aufsichtsbehörde über die Verarbeitung Ihrer personenbezogenen Daten durch uns zu beschweren. Zuständige Aufsichtsbehörde ist:

        Der Hessische Beauftragte für Datenschutz und Informationsfreiheit

        Gustav-Stresemann-Ring 1  
        65189 Wiesbaden

        Tel. 0611 1408 0

        [poststelle@datenschutz.hessen.de](mailto:poststelle@datenschutz.hessen.de "mailto:poststelle@datenschutz.hessen.de")

        Gegebenenfalls werden die Unterlagen vom Universitätsarchiv übernommen und dort in der Regel unbegrenzt aufbewahrt.

    """.trimIndent()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateTemplateRequest("imprint", "default value by ag", imprint))
        service.create(CreateTemplateRequest("contact", "default value by ag", contact))
        service.create(CreateTemplateRequest("agb", "default value by ag", agb))
        service.create(CreateTemplateRequest("about", "default value by ag", about))
        service.create(CreateTemplateRequest("datenschutzverordnung", "default value by ag", datenschutzverordnung))
    }

}