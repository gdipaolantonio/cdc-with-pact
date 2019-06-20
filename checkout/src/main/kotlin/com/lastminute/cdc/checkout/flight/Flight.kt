package com.lastminute.cdc.checkout.flight

import java.math.BigDecimal
import java.util.*

data class Flight(
  val id: UUID,
  val price: Money
)

data class Money(
  val amount: BigDecimal,
  val currency: String
)