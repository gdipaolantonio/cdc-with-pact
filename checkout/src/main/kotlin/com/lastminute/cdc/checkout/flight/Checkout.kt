package com.lastminute.cdc.checkout.flight

import java.net.URI
import java.util.*

interface Checkout {
  fun checkoutFlight(flightId: UUID, userId: String)
}
