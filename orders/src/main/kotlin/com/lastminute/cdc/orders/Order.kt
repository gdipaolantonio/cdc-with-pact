package com.lastminute.cdc.orders

import java.util.*

data class Order(
  val orderId: UUID,
  val flightId: UUID,
  val userId: String
)
