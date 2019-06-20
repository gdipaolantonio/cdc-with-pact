package com.lastminute.cdc.flights.search

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.target.Target
import au.com.dius.pact.provider.junit.target.TestTarget
import au.com.dius.pact.provider.spring.SpringRestPactRunner
import au.com.dius.pact.provider.spring.target.MockMvcTarget
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.*

@RunWith(SpringRestPactRunner::class)
@Provider("flights")
@PactBroker(host = "192.168.99.100", port = "9292")
class FlightSearchControllerPactTest {
  private val results: MutableList<Flight> = mutableListOf()

  @TestTarget
  @JvmField
  val target: Target = MockMvcTarget(
    controllers = listOf(
      FlightSearchController(InMemoryFlightsRepository(results))
    )
  )

  @State(
    "available results for MIL-LON on 2019-06-16",
    "not available results for MIL-LON on 2019-06-21",
    "found flight with id 4bded7c6-284e-4be5-8b9b-76813acb4b0b"
  )
  fun `with 4bded7c6-284e-4be5-8b9b-76813acb4b0b id`() {
    results.clear()
    results.add(
      Flight(
        id = UUID.fromString("4bded7c6-284e-4be5-8b9b-76813acb4b0b"),
        departure = Departure(
          time = "2019-06-16T20:00:00.000Z",
          airport = "BGY",
          group = "MIL"
        ),
        arrival = Arrival(
          time = "2019-06-16T21:30:00.000Z",
          airport = "LTN",
          group = "LON"
        ),
        price = Money(
          amount = BigDecimal("50.00"),
          currency = "EUR"
        )
      )
    )
  }
}