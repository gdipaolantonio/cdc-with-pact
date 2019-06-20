package com.lastminute.cdc.flights.search

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal.TEN
import java.time.LocalDate
import java.util.*

class InMemoryFlightsRepositoryTest {
  @Test
  fun `load by request params with results on different days`() {
    val toBeDiscarded = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-21T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-21T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val toBeSelected = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        toBeDiscarded,
        toBeSelected
      )
    )

    val flights = repository.loadBy("MXP", "STN", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactly(toBeSelected)
  }

  @Test
  fun `load by request params with results on different departure airport`() {
    val toBeDiscarded = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "BGY",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val toBeSelected = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        toBeDiscarded,
        toBeSelected
      )
    )

    val flights = repository.loadBy("MXP", "STN", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactly(toBeSelected)
  }

  @Test
  fun `load by request params with results on different arrival airport`() {
    val toBeDiscarded = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "GTW",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val toBeSelected = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        toBeDiscarded,
        toBeSelected
      )
    )

    val flights = repository.loadBy("MXP", "STN", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactly(toBeSelected)
  }

  @Test
  fun `load by request params with results including airports in departure group`() {
    val flightDepartingFromPescara = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "PSR",
        null
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val flightDepartingFromFiumicino = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "FCO",
        "ROM"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val flightDepartingFromBergamo = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "BGY",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val flightDepartingFromMalpensa = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        flightDepartingFromPescara,
        flightDepartingFromFiumicino,
        flightDepartingFromBergamo,
        flightDepartingFromMalpensa
      )
    )

    val flights = repository.loadBy("MIL", "STN", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactlyInAnyOrder(flightDepartingFromBergamo, flightDepartingFromMalpensa)
  }

  @Test
  fun `load by request params with results including airports in arrival group`() {
    val flightArrivingToPescara = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "STN",
        "LON"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "PSR",
        null
      ),
      Money(TEN, "EUR")
    )
    val flightArrivingToFiumicino = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "STN",
        "LON"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "FCO",
        "ROM"
      ),
      Money(TEN, "EUR")
    )
    val flightArrivingToBergamo = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "STN",
        "LON"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "BGY",
        "MIL"
      ),
      Money(TEN, "EUR")
    )
    val flightArrivingToMalpensa = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "STN",
        "LON"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "MXP",
        "MIL"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        flightArrivingToPescara,
        flightArrivingToFiumicino,
        flightArrivingToBergamo,
        flightArrivingToMalpensa
      )
    )

    val flights = repository.loadBy("STN", "MIL", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactlyInAnyOrder(flightArrivingToBergamo, flightArrivingToMalpensa)
  }

  @Test
  fun `load MIL-LON`() {
    val mxp_stn = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val bgy_ltn = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "BGY",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "LTN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val fco_cgy = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "FCO",
        "ROM"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "CGY",
        "PAR"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(mxp_stn, bgy_ltn, fco_cgy)
    )

      val flights = repository.loadBy("MIL", "LON", LocalDate.parse("2019-06-20"))

    assertThat(flights).containsExactlyInAnyOrder(mxp_stn, bgy_ltn)
  }

  @Test
  fun `load by request params without results`() {
    val toBeDiscarded = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-21T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-21T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val toBeSelected = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(
      listOf(
        toBeDiscarded,
        toBeSelected
      )
    )

    val flights = repository.loadBy("ROM", "LON", LocalDate.parse("2019-06-20"))

    assertThat(flights).isEmpty()
  }

  @Test
  fun `load by id`() {
    val unexpected = Flight(
      UUID.randomUUID(),
      Departure(
        "2019-06-21T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-21T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )
    val id = UUID.fromString("4bded7c6-284e-4be5-8b9b-76813acb4b0b")
    val expected = Flight(
      id,
      Departure(
        "2019-06-20T10:00:00.000Z",
        "MXP",
        "MIL"
      ),
      Arrival(
        "2019-06-20T11:30:00.000Z",
        "STN",
        "LON"
      ),
      Money(TEN, "EUR")
    )

    val repository = InMemoryFlightsRepository(listOf(unexpected, expected))

    val flight = repository.loadBy(id)

    assertThat(flight).isEqualTo(expected)
  }
}
