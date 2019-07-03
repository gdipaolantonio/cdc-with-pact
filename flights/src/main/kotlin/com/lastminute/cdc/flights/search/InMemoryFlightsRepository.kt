package com.lastminute.cdc.flights.search

import java.time.LocalDate
import java.util.*

class InMemoryFlightsRepository : FlightsRepository {

  private val flightsList: MutableList<Flight> = mutableListOf()

  override fun loadBy(departure: String, arrival: String, date: LocalDate): List<Flight> {
    return flightsList.filter { flight ->
      flight.departureDate() == date &&
      (flight.departure.airport == departure || flight.departure.group == departure) &&
      (flight.arrival.airport == arrival || flight.arrival.group == arrival)
    }
  }

  override fun loadBy(id: UUID): Flight? {
    return flightsList.find { flight -> flight.id == id }
  }

  fun add(vararg flights: Flight) {
    flightsList.addAll(flights)
  }

  fun clear() {
    flightsList.clear()
  }
}