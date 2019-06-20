package com.lastminute.cdc.frontend.flight.search

import com.lastminute.cdc.frontend.flight.Flight
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.LocalDate

class FlightsSearch(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/flights/{departure}/{arrival}/?date={date}"

  fun by(departure: String, arrival: String, date: LocalDate): List<Flight>? {
    return try {
      loadFromProvider(departure, arrival, date).flights
    }
    catch (e: HttpClientErrorException.NotFound) {
      emptyList()
    }
  }

  private fun loadFromProvider(departure: String, arrival: String, date: LocalDate) =
    restTemplate.getForEntity(url, Response::class.java, departure, arrival, date).body!!

  data class Response(
    val flights: List<Flight>
  )
}