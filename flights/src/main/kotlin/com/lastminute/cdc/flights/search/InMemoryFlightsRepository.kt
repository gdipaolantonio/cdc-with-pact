package com.lastminute.cdc.flights.search

import java.time.LocalDate

class InMemoryFlightsRepository(
  private val results: Map<Key, List<Flight>>
) : FlightsRepository {
  override fun loadBy(departure: String, arrival: String, date: LocalDate) =
    results[Key(departure, arrival, date)]

  data class Key(
    val departure: String,
    val arrival: String,
    val date: LocalDate
  )
}