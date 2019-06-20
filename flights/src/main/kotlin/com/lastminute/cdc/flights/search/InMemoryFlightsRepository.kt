package com.lastminute.cdc.flights.search

import java.time.LocalDate
import java.util.*

class InMemoryFlightsRepository(
  private val flights: List<Flight> = emptyList()
) : FlightsRepository {
  override fun loadBy(departure: String, arrival: String, date: LocalDate): List<Flight> {
    return flights.filter { flight ->
      flight.departureDate() == date &&
      (flight.departure.airport == departure || flight.departure.group == departure) &&
      (flight.arrival.airport == arrival || flight.arrival.group == arrival)
    }
  }

  override fun loadBy(id: UUID): Flight? {
    return flights.find { flight -> flight.id == id }
  }
}