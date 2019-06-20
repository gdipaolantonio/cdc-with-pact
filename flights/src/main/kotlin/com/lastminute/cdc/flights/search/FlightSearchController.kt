package com.lastminute.cdc.flights.search

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class FlightSearchController(
  private val flightsRepository: FlightsRepository
) {
  @GetMapping("/flights/{departure}/{arrival}")
  fun searchFlights(
    @PathVariable("departure") departure: String,
    @PathVariable("arrival") arrival: String,
    @RequestParam("date") date: String
  ): ResponseEntity<Response> {
    return ResponseEntity.ok(Response(flightsRepository.loadBy(departure, arrival, LocalDate.parse(date))!!))
  }

  data class Response(
    val flights: List<Flight>
  )
}