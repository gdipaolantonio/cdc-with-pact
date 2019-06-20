package com.lastminute.cdc.flights.search

import java.math.BigDecimal
import java.util.*

data class Flight(
  val id: UUID,
  val departure: Departure,
  val arrival: Arrival,
  val price: Money
)

data class Departure(
  val time: String,
  val airport: String
)

data class Arrival(
  val time: String,
  val airport: String
)

data class Money(
  val amount: BigDecimal,
  val currency: String
)