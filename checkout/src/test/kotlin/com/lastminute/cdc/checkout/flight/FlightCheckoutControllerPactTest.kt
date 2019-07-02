package com.lastminute.cdc.checkout.flight

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.target.Target
import au.com.dius.pact.provider.junit.target.TestTarget
import au.com.dius.pact.provider.spring.SpringRestPactRunner
import au.com.dius.pact.provider.spring.target.MockMvcTarget
import org.junit.runner.RunWith
import java.util.*

@RunWith(SpringRestPactRunner::class)
@Provider("checkout")
@PactBroker(host = "192.168.99.100", port = "9292")
class FlightCheckoutControllerPactTest {
  private val checkout: InMemoryCheckout = InMemoryCheckout()

  @TestTarget
  @JvmField
  val target: Target = MockMvcTarget(
    controllers = listOf(
      FlightCheckoutController(checkout)
    )
  )

  @State("found flight with id 47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
  fun `with flight with id 47e2f0e6-a848-48c8-b1ab-e3dd80a80829`() {
    checkout.accept(
      UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829"),
      "alice"
    )
  }
}