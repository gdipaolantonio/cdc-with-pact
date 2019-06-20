package com.lastminute.cdc.frontend.flights

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Flight(
  val id: UUID,
  val departure: Departure,
  val arrival: Arrival,
  val price: Money
)

data class Departure(
  val time: Instant,
  val airport: String
)

data class Arrival(
  val time: Instant,
  val airport: String
)

data class Money(
  val amount: BigDecimal,
  val currency: String
)