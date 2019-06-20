package com.lastminute.cdc.flights.search

import au.com.dius.pact.provider.junit.Consumer
import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.target.Target
import au.com.dius.pact.provider.junit.target.TestTarget
import au.com.dius.pact.provider.spring.SpringRestPactRunner
import au.com.dius.pact.provider.spring.target.MockMvcTarget
import com.lastminute.cdc.flights.search.InMemoryFlightsRepository.Key
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.*

@RunWith(SpringRestPactRunner::class)
@Provider("flights")
@Consumer("frontend")
@PactBroker(host = "192.168.99.100", port = "9292")
class FlightSearchControllerPactTest {
  private val results: MutableMap<Key, List<Flight>> = mutableMapOf()

  @TestTarget
  @JvmField
  val target: Target = MockMvcTarget(
    controllers = listOf(
      FlightSearchController(InMemoryFlightsRepository(results))
    )
  )

  @State("found results for MIL-LON on 2019-06-16")
  fun `with found results`() {
    results.clear()
    results[Key("MIL", "LON", LocalDate.parse("2019-06-16"))] = listOf(
      Flight(
        id = UUID.randomUUID(),
        departure = Departure(
          time = Instant.now().toString(),
          airport = "MXP"
        ),
        arrival = Arrival(
          time = Instant.now().toString(),
          airport = "STN"
        ),
        price = Money(
          amount = BigDecimal("50.00"),
          currency = "EUR"
        )
      )
    )
  }
}