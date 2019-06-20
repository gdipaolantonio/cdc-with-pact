package com.lastminute.cdc.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrdersApplication

fun main(args: Array<String>) {
	runApplication<OrdersApplication>(*args)
}
