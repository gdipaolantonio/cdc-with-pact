package com.lastminute.cdc.flights.search

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
class FlightSearchController(
  private val flightsRepository: FlightsRepository
) {
  @GetMapping("/flights/{departure}/{arrival}")
  fun searchFlights(
    @PathVariable("departure") departure: String,
    @PathVariable("arrival") arrival: String,
    @RequestParam("date") date: String
  ): ResponseEntity<SearchResponse> {
    return ResponseEntity.ok(SearchResponse(flightsRepository.loadBy(departure, arrival, LocalDate.parse(date))))
  }

  @GetMapping("/flights/{id}/")
  fun searchFlights(
    @PathVariable("id") id: UUID
  ): ResponseEntity<Flight> {
    return ResponseEntity.ok(flightsRepository.loadBy(id)!!)
  }

  data class SearchResponse(
    val flights: List<Flight>
  )
}