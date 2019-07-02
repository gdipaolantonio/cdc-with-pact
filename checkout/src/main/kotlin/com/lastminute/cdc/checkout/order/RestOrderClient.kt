package com.lastminute.cdc.checkout.order

import org.springframework.web.client.RestTemplate
import java.util.*

class RestOrderClient(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/orders/flight/"

  fun createOrder(flightId: UUID, userId: String) {
    restTemplate.postForEntity(url, Request(flightId, userId), Void::class.java)
  }

  data class Request(
    val flightId: UUID,
    val userId: String
  )
}