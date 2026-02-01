package de.csw.turtle.api.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

private const val FROM = "noreply@csw.du-darmstadt.de"

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {

    fun sendSimpleEmail(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = subject
        message.text = text
        message.from = FROM
        mailSender.send(message)
    }

    fun sendHtmlEmail(to: String, subject: String, text: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setFrom(FROM)
        helper.setText(text, true)

        mailSender.send(message)
    }

}