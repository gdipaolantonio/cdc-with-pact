package com.lastminute.cdc.checkout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CheckoutApplication

fun main(args: Array<String>) {
	runApplication<CheckoutApplication>(*args)
}
