package com.lastminute.cdc.frontend.flights

import org.springframework.web.client.RestTemplate
import java.time.LocalDate

class RestFlightsRepository(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/flights/{departure}/{arrival}/?date={date}"

  fun searchBy(departure: String, arrival: String, date: LocalDate): List<Flight>? {
    val responseEntity = restTemplate.getForEntity(url, Response::class.java, departure, arrival, date)
    return responseEntity.body!!.flights
  }

  data class Response(
    val flights: List<Flight>
  )
}