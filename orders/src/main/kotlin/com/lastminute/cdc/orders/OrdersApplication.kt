package com.lastminute.cdc.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class OrdersApplication {
	@Bean
	fun orders() = InMemoryOrders()
}

fun main(args: Array<String>) {
	runApplication<OrdersApplication>(*args)
}
