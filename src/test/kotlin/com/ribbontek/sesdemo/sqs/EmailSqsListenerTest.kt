package com.ribbontek.sesdemo.sqs

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import com.ribbontek.sesdemo.config.SesClientConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmailSqsListenerTest {

    @Autowired
    private lateinit var amazonSimpleEmailService: AmazonSimpleEmailService

    @Autowired
    private lateinit var sesClientConfig: SesClientConfig

    @Test
    fun `bounce queue - handle successfully`() {
        val recipientEmail = "bounce@simulator.amazonses.com"
        val subject = Content().withData("BOUNCE EMAIL")
        val body = Body().withText(Content().withData("This is a test email."))

        val sendEmailRequest = SendEmailRequest()
            .withSource(sesClientConfig.senderEmail)
            .withReplyToAddresses(sesClientConfig.senderEmail)
            .withDestination(Destination().withToAddresses(recipientEmail))
            .withMessage(Message().withSubject(subject).withBody(body))
            .withConfigurationSetName("SesDemoSESConfigurationSet")

        amazonSimpleEmailService.sendEmail(sendEmailRequest)

        // Could add polling here if saving the result to the DB to ensure the service is picking up the message
        Thread.sleep(10000)
    }

    @Test
    fun `complaint queue - handle successfully`() {
        val recipientEmail = "complaint@simulator.amazonses.com"
        val subject = Content().withData("COMPLAINT EMAIL")
        val body = Body().withText(Content().withData("This is a test email."))

        val sendEmailRequest = SendEmailRequest()
            .withSource(sesClientConfig.senderEmail)
            .withReplyToAddresses(sesClientConfig.senderEmail)
            .withDestination(Destination().withToAddresses(recipientEmail))
            .withMessage(Message().withSubject(subject).withBody(body))
            .withConfigurationSetName("SesDemoSESConfigurationSet")

        amazonSimpleEmailService.sendEmail(sendEmailRequest)

        // Could add polling here if saving the result to the DB to ensure the service is picking up the message
        Thread.sleep(10000)
    }
}
