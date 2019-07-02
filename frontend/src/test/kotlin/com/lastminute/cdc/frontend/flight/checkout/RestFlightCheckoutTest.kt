package com.lastminute.cdc.frontend.flight.checkout

import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.model.RequestResponsePact
import com.lastminute.cdc.frontend.runWith
import io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody
import org.junit.Test
import org.springframework.web.client.RestTemplate
import java.util.*

class RestFlightCheckoutTest {
  private val flightId = UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
  private val userId = "alice"

  private val checkoutSuccessfullyPact: RequestResponsePact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("checkout")

    .given("found flight with id $flightId")

    .uponReceiving("request to checkout flight with id $flightId for user $userId")
    .method("POST")
    .headers(mapOf("Content-Type" to "application/json"))
    .path("/checkout/flight/")
    .body(newJsonBody { root ->
      root.uuid("flightId", flightId)
      root.stringType("userId", userId)
    }.build())

    .willRespondWith()
    .status(202)

    .toPact()

  @Test
  fun `checkout successfully for a user`() {
    runWith(checkoutSuccessfullyPact) { mockServer ->
      val flightCheckout = RestFlightCheckout(RestTemplate(), mockServer.getUrl())
      flightCheckout.checkoutFlight(flightId, userId)
    }
  }
}