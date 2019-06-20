package com.lastminute.cdc.flights.search

import java.time.LocalDate

interface FlightsRepository {
  fun loadBy(departure: String, arrival: String, date: LocalDate): List<Flight>?
}