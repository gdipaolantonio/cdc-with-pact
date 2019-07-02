package com.lastminute.cdc.frontend.flight.checkout

import org.springframework.web.client.RestTemplate
import java.util.*

class RestFlightCheckout(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/checkout/"

  fun checkoutFlight(flightId: UUID, userId: String) {
    restTemplate.postForEntity(url, Request(flightId, userId), Void::class.java)
  }

  data class Request(
    val flightId: UUID,
    val userId: String
  )
}