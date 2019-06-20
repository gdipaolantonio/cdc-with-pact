package com.lastminute.cdc.checkout

import au.com.dius.pact.consumer.ConsumerPactBuilder
import io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class RestFlightRepositoryTest {
  private val flightId = UUID.fromString("4bded7c6-284e-4be5-8b9b-76813acb4b0b")
  private val flightDepartureTime = Instant.parse("2019-06-16T18:00:00Z")
  private val flightDepartureAirport = "MXP"
  private val flightArrivalTime = Instant.parse("2019-06-16T20:00:00Z")
  private val flightArrivalAirport = "STN"
  private val priceAmount = BigDecimal(50.00)
  private val currency = "EUR"

  private val foundFlightPact = ConsumerPactBuilder.consumer("checkout")
    .hasPactWith("flights")

    .given("found flight with id $flightId")
    .uponReceiving("request for a flight")
    .method("GET")
    .path("/flights/$flightId/")

    .willRespondWith()
    .status(200)
    .headers(mapOf("Content-Type" to "application/json"))
    .body(
      newJsonBody { flight ->
        flight.uuid("id", flightId)

        flight.`object`("departure") { departure ->
          departure.timestamp("time", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", Date.from(flightDepartureTime), TimeZone.getTimeZone("Z"))
          departure.stringType("airport", flightDepartureAirport)
        }

        flight.`object`("arrival") { arrival ->
          arrival.timestamp("time", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", Date.from(flightArrivalTime), TimeZone.getTimeZone("Z"))
          arrival.stringType("airport", flightArrivalAirport)
        }

        flight.`object`("price") { price ->
          price.decimalType("amount", priceAmount)
          price.stringType("currency", currency)
        }
      }
      .build()
    )
    .toPact()

  @Test
  fun `with found flight`() {
    val expected = Flight(
      id = flightId,
      price = Money(
        amount = priceAmount,
        currency = currency
      )
    )

    val flight = runWith(foundFlightPact) { mockServer ->
      val repository = RestFlightRepository(RestTemplate(), mockServer.getUrl())

      repository.getBy(flightId)
    }

    assertThat(flight).isEqualTo(expected)
  }
}