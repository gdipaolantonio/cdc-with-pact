package com.lastminute.cdc.checkout.order

import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.model.RequestResponsePact
import com.lastminute.cdc.checkout.runWith
import io.pactfoundation.consumer.dsl.LambdaDsl
import org.junit.Test
import org.springframework.web.client.RestTemplate
import java.util.*

class RestOrderClientTest {
  private val flightId = UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
  private val userId = "alice"

  private val createOrderSuccessfullyPact: RequestResponsePact = ConsumerPactBuilder.consumer("checkout")
    .hasPactWith("orders")

    .given("can create order for flight $flightId and $userId")

    .uponReceiving("valid request for order creation")
    .method("POST")
    .path("/orders/flight/")
    .headers(mapOf("Content-Type" to "application/json"))
    .body(LambdaDsl.newJsonBody { root ->
      root.uuid("flightId", flightId)
      root.stringType("userId", userId)
    }.build())

    .willRespondWith()
    .status(201)

    .toPact()

  @Test
  fun `create order successfully`() {
    runWith(createOrderSuccessfullyPact) { mockServer ->
      val client = RestOrderClient(RestTemplate(), mockServer.getUrl())
      client.createOrder(flightId, userId)
    }
  }
}