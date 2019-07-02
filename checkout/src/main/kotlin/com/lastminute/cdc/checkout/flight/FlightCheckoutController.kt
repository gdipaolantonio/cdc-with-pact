package com.lastminute.cdc.checkout.flight

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("/checkout/flight/")
class FlightCheckoutController(
  private val checkout: Checkout
) {
  @PostMapping
  fun checkoutFlight(@RequestBody requestBody: RequestJson): ResponseEntity<Void> {
    checkout.checkoutFlight(requestBody.flightId, requestBody.userId)
    return ResponseEntity.accepted().build()
  }

  data class RequestJson(
    val flightId: UUID,
    val userId: String
  )
}