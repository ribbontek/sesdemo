package com.ribbontek.sesdemo.config

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

data class SesClientConfig(
    val region: String,
    val senderEmail: String
)

@Configuration
@EnableSqs
class SesConfig(
    @Value("\${com.ribbontek.ses.region}") private val sesRegion: String,
    @Value("\${com.ribbontek.ses.sender}") private val senderEmail: String
) {

    @Autowired
    private lateinit var simpleMessageListenerContainer: SimpleMessageListenerContainer

    @PostConstruct
    fun postConstruct() {
        simpleMessageListenerContainer.queueStopTimeout = 20000 // removes timeout exceptions shutting down queues
    }

    @Bean
    fun amazonSimpleEmailService(): AmazonSimpleEmailService {
        return AmazonSimpleEmailServiceClient.builder()
            .withCredentials(DefaultAWSCredentialsProviderChain())
            .withRegion(sesRegion)
            .build()
    }

    @Bean
    fun sesClientConfig(): SesClientConfig {
        return SesClientConfig(
            region = sesRegion,
            senderEmail = senderEmail
        )
    }
}
