package com.lastminute.cdc.flights.search

import java.time.LocalDate
import java.util.*

interface FlightsRepository {
  fun loadBy(departure: String, arrival: String, date: LocalDate): List<Flight>
  fun loadBy(id: UUID): Flight?
}