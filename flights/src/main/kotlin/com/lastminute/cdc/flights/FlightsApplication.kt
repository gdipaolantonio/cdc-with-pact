package com.lastminute.cdc.flights

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlightsApplication

fun main(args: Array<String>) {
	runApplication<FlightsApplication>(*args)
}
