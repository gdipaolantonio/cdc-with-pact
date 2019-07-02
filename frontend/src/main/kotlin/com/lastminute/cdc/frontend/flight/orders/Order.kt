package com.lastminute.cdc.frontend.flight.orders

import java.util.*

data class Order(
  val orderId: UUID,
  val flightId: UUID
)
