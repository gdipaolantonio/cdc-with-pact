package com.lastminute.cdc.checkout.flight

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.*

class RestFlightRepository(
  private val restTemplate: RestTemplate,
  baseUrl: String
) {
  private val url = "$baseUrl/flights/{id}/"

  fun getBy(id: UUID): Flight? {
    return try {
      restTemplate.getForEntity(url, Flight::class.java, id).body
    }
    catch (e: HttpClientErrorException) {
      null
    }
  }
}