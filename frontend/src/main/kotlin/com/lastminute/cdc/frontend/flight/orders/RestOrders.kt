package com.lastminute.cdc.frontend.flight.orders

import org.springframework.web.client.RestTemplate

class RestOrders(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/orders/{userId}/"

  fun ordersFor(userId: String): List<Order> = loadFromProvider(userId).orders

  private fun loadFromProvider(userId: String) =
    restTemplate.getForEntity(url, Response::class.java, userId).body!!

  data class Response(
    val orders: List<Order>
  )
}