package com.lastminute.cdc.checkout

import com.lastminute.cdc.checkout.flight.InMemoryCheckout
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class CheckoutApplication {
	@Bean
	fun checkout() = InMemoryCheckout()
}

fun main(args: Array<String>) {
	runApplication<CheckoutApplication>(*args)
}
