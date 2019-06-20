package com.lastminute.cdc.flights

import com.lastminute.cdc.flights.search.InMemoryFlightsRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class FlightsApplication {
	@Bean
	fun flightsRepository() = InMemoryFlightsRepository(emptyList())
}

fun main(args: Array<String>) {
	runApplication<FlightsApplication>(*args)
}
