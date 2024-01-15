package com.ribbontek.sesdemo.sqs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class EmailSqsListener(
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @SqsListener("\${com.ribbontek.sqs.queue.bounce.uri}")
    fun handleBounceMessage(message: String) {
        try {
            log.info("Received bounce message: $message")
            val sesNotification = message.fromJson(SesNotification::class.java)
            val sesMessage = sesNotification.message.fromJson(SesMessage::class.java)
            log.info("BOUNCE Ses Message: $sesMessage")
            log.info("Successfully record bounce for email message with id: ${sesMessage.mail.messageId}")
        } catch (ex: Exception) {
            log.error("Error processing event", ex)
            throw ex
        }
    }

    @SqsListener("\${com.ribbontek.sqs.queue.complaint.uri}")
    fun handleComplaintMessage(message: String) {
        try {
            log.info("Received complaint message: $message")
            val sesNotification = message.fromJson(SesNotification::class.java)
            val sesMessage = sesNotification.message.fromJson(SesMessage::class.java)
            log.info("COMPLAINT Ses Message: $sesMessage")
            log.info("Successfully record bounce for email message with id: ${sesMessage.mail.messageId}")
        } catch (ex: Exception) {
            log.error("Error processing event", ex)
            throw ex
        }
    }

    private fun <T> String.fromJson(clazz: Class<T>): T = objectMapper.readValue(this, clazz)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SesNotification(
    @JsonProperty("Type")
    val type: String,
    @JsonProperty("MessageId")
    val messageId: String,
    @JsonProperty("Subject")
    val subject: String,
    @JsonProperty("Message")
    val message: String,
    @JsonProperty("Timestamp")
    val timestamp: ZonedDateTime
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SesMessage(
    val eventType: String,
    val complaint: HashMap<String, *>?,
    val bounce: HashMap<String, *>?,
    val mail: SesMail
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SesMail(
    val source: String,
    val messageId: String,
    val destination: List<String>
)
