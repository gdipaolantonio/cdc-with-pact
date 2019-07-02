package com.lastminute.cdc.frontend.flight.search

import au.com.dius.pact.consumer.ConsumerPactBuilder
import com.lastminute.cdc.frontend.flight.Arrival
import com.lastminute.cdc.frontend.flight.Departure
import com.lastminute.cdc.frontend.flight.Flight
import com.lastminute.cdc.frontend.flight.Money
import com.lastminute.cdc.frontend.runWith
import io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.*

class RestFlightsSearchTest {
  private val requestDeparture: String = "MIL"
  private val requestArrival: String = "LON"
  private val requestDate: LocalDate = LocalDate.parse("2019-06-16")
  private val notAvailableRequestDate: LocalDate = LocalDate.parse("2019-06-21")

  private val flightId = UUID.fromString("4bded7c6-284e-4be5-8b9b-76813acb4b0b")
  private val notAvailableFlightId = UUID.fromString("ef79d467-4864-432a-92c7-533e731cd6c3")
  private val flightDepartureTime = Instant.parse("2019-06-16T18:00:00Z")
  private val flightDepartureAirport = "MXP"
  private val flightArrivalTime = Instant.parse("2019-06-16T20:00:00Z")
  private val flightArrivalAirport = "STN"
  private val priceAmount = BigDecimal(50.00)
  private val currency = "EUR"

  private val availableFlightsPact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("flights")

    .given("available results for $requestDeparture-$requestArrival on $requestDate")
    .uponReceiving("request for flights")
    .method("GET")
    .path("/flights/$requestDeparture/$requestArrival/")
    .query("date=$requestDate")

    .willRespondWith()
    .status(200)
    .headers(mapOf("Content-Type" to "application/json"))
    .body(
      newJsonBody { root ->
        root.array("flights") { array ->
          array.`object` { flight ->
            flight.stringType("id", flightId.toString())

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
        }
      }
        .build()
    )
    .toPact()

  private val notAvailableFlightsPact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("flights")

    .given("not available results for $requestDeparture-$requestArrival on $notAvailableRequestDate")
    .uponReceiving("request for flights")
    .method("GET")
    .path("/flights/$requestDeparture/$requestArrival/")
    .query("date=$notAvailableRequestDate")

    .willRespondWith()
    .status(404)
    .toPact()

  private val availableFlightByIdPact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("flights")

    .given("available flight with id $flightId")

    .uponReceiving("request for flight by id")
    .method("GET")
    .path("/flights/$flightId/")

    .willRespondWith()
    .status(200)
    .body(newJsonBody { flight ->
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
    }.build())

    .toPact()

  private val notAvailableFlightByIdPact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("flights")

    .given("not available flight with id $notAvailableFlightId")

    .uponReceiving("request for flight by id")
    .method("GET")
    .path("/flights/$notAvailableFlightId/")

    .willRespondWith()
    .status(404)

    .toPact()

  @Test
  fun `with at least one flight found`() {
    val expected = Flight(
      id = flightId,
      departure = Departure(
        time = flightDepartureTime,
        airport = flightDepartureAirport
      ),
      arrival = Arrival(
        time = flightArrivalTime,
        airport = flightArrivalAirport
      ),
      price = Money(
        amount = priceAmount,
        currency = currency
      )
    )

    val flights: List<Flight>? = runWith(availableFlightsPact) { mockServer ->
      val flightsSearch = RestFlightsSearch(RestTemplate(), mockServer.getUrl())

      flightsSearch.by(
        departure = requestDeparture,
        arrival = requestArrival,
        date = requestDate
      )
    }

    assertThat(flights).containsExactly(expected)
  }

  @Test
  fun `without available flights`() {
    val flights: List<Flight>? = runWith(notAvailableFlightsPact) { mockServer ->
      val flightsSearch = RestFlightsSearch(RestTemplate(), mockServer.getUrl())

      flightsSearch.by(
        departure = requestDeparture,
        arrival = requestArrival,
        date = notAvailableRequestDate
      )
    }

    assertThat(flights).isEmpty()
  }

  @Test
  fun `with one flight found by id`() {
    val expected = Flight(
      id = flightId,
      departure = Departure(
        time = flightDepartureTime,
        airport = flightDepartureAirport
      ),
      arrival = Arrival(
        time = flightArrivalTime,
        airport = flightArrivalAirport
      ),
      price = Money(
        amount = priceAmount,
        currency = currency
      )
    )

    val flight: Flight? = runWith(availableFlightByIdPact) { mockServer ->
      val flightsSearch = RestFlightsSearch(RestTemplate(), mockServer.getUrl())

      flightsSearch.by(flightId)
    }

    assertThat(flight).isEqualTo(expected)
  }

  @Test
  fun `with flight not found by id`() {
    val flight: Flight? = runWith(notAvailableFlightByIdPact) { mockServer ->
      val flightsSearch = RestFlightsSearch(RestTemplate(), mockServer.getUrl())

      flightsSearch.by(notAvailableFlightId)
    }

    assertThat(flight).isNull()
  }
}
