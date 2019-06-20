package com.lastminute.cdc.flights.search

import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.util.*

data class Flight(
  val id: UUID,
  val departure: Departure,
  val arrival: Arrival,
  val price: Money
) {
  internal fun departureDate() = Instant.parse(departure.time).atZone(ZoneId.of("Z")).toLocalDate()
}

data class Departure(
  val time: String,
  val airport: String,
  val group: String?
)

data class Arrival(
  val time: String,
  val airport: String,
  val group: String?
)

data class Money(
  val amount: BigDecimal,
  val currency: String
)