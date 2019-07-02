package com.lastminute.cdc.orders

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
class OrdersController(
  private val orders: Orders
) {
  @PostMapping("/orders/")
  fun createOrderForFlight(@RequestBody requestBody: CreateOrderJsonRequest): ResponseEntity<Void> {
    orders.create(requestBody.flightId, requestBody.userId)
    return ResponseEntity.created(URI.create("http://any.location.com")).build()
  }

  @GetMapping("/orders/{userId}/")
  fun getOrdersForUser(@PathVariable("userId") userId: String): ResponseEntity<OrdersJsonResponse> {
    val ordersList = orders.getBy(userId)
    return ResponseEntity.ok(OrdersJsonResponse(ordersList))
  }

  data class CreateOrderJsonRequest(
    val flightId: UUID,
    val userId: String
  )

  data class OrdersJsonResponse(
    val orders: List<Order>
  )
}