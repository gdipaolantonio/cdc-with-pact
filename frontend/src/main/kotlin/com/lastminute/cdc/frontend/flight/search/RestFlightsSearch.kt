package com.lastminute.cdc.frontend.flight.search

import com.lastminute.cdc.frontend.flight.Flight
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*

class RestFlightsSearch(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val searchUrl = "$baseUrl/flights/{departure}/{arrival}/?date={date}"
  private val loadUrl = "$baseUrl/flights/{flightId}/"

  fun by(departure: String, arrival: String, date: LocalDate): List<Flight>? {
    return try {
      searchFromProvider(departure, arrival, date).flights
    }
    catch (e: HttpClientErrorException.NotFound) {
      emptyList()
    }
  }

  private fun searchFromProvider(departure: String, arrival: String, date: LocalDate) =
    restTemplate.getForEntity(searchUrl, SearchResponse::class.java, departure, arrival, date).body!!

  data class SearchResponse(
    val flights: List<Flight>
  )

  fun by(flightId: UUID): Flight? {
    return try {
      loadFromProvider(flightId)
    }
    catch (e: HttpClientErrorException.NotFound) {
      null
    }
  }

  private fun loadFromProvider(flightId: UUID) =
    restTemplate.getForEntity(loadUrl, Flight::class.java, flightId).body
}