package com.lastminute.cdc.checkout.flight

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class InMemoryCheckoutTest {
  @get:Rule
  val expectedExceptionRule: ExpectedException = ExpectedException.none()

  private val checkout = InMemoryCheckout()

  @Test
  fun `with accepted flightId and userId`() {
    val acceptedFlightId = UUID.randomUUID()
    val acceptedUserId = "alice"
    checkout.accept(acceptedFlightId, acceptedUserId)

    checkout.checkoutFlight(acceptedFlightId, acceptedUserId)

    assertThat(checkout.wasCalledWith(acceptedFlightId, acceptedUserId)).isTrue()
  }

  @Test
  fun `with not accepted flightId and userId`() {
    val acceptedFlightId = UUID.randomUUID()
    val acceptedUserId = "alice"

    checkout.accept(acceptedFlightId, acceptedUserId)

    val notAcceptedFlightId = UUID.randomUUID()
    val notAcceptedUserId = "bob"

    expectedExceptionRule.expect(CheckoutException::class.java)
    expectedExceptionRule.expectMessage(notAcceptedFlightId.toString())
    expectedExceptionRule.expectMessage(notAcceptedUserId)

    checkout.checkoutFlight(notAcceptedFlightId, notAcceptedUserId)

    assertThat(checkout.wasCalledWith(notAcceptedFlightId, notAcceptedUserId)).isTrue()
    assertThat(checkout.wasCalledWith(acceptedFlightId, acceptedUserId)).isFalse()
  }
}