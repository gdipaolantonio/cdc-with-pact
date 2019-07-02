package com.lastminute.cdc.checkout.flight

import java.util.*

class InMemoryCheckout : Checkout {
  private var acceptedFlightId: UUID? = null
  private var acceptedUserId: String? = null

  private var calledFlightId: UUID? = null
  private var calledUserId: String? = null

  override fun checkoutFlight(flightId: UUID, userId: String) {
    calledFlightId = flightId
    calledUserId = userId
    if (flightId != acceptedFlightId && userId != acceptedUserId) {
      throw CheckoutException(flightId, userId)
    }
  }

  fun accept(flightId: UUID, userId: String) {
    acceptedFlightId = flightId
    acceptedUserId = userId
  }

  fun wasCalledWith(flightId: UUID, userId: String): Boolean {
    return flightId == calledFlightId && userId == calledUserId
  }
}
