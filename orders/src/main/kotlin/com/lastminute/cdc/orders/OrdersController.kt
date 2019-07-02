package com.lastminute.cdc.orders

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class OrdersController(
  private val orders: Orders
) {
  @PostMapping("/orders/flight/")
  fun createOrderForFlight(@RequestBody requestBody: RequestJson): ResponseEntity<Void> {
    orders.create(requestBody.flightId, requestBody.userId)
    return ResponseEntity.created(URI.create("http://any.location.com")).build()
  }

  data class RequestJson(
    val flightId: UUID,
    val userId: String
  )
}