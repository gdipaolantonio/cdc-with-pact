package com.lastminute.cdc.frontend.flight.orders

import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.model.RequestResponsePact
import com.lastminute.cdc.frontend.runWith
import io.pactfoundation.consumer.dsl.LambdaDsl.*
import org.assertj.core.api.Assertions
import org.junit.Test
import org.springframework.web.client.RestTemplate
import java.util.*

class RestOrdersTest {
  private val orderId = UUID.fromString("3619a9bb-8ab2-4a41-a5c2-7e6d10816ba5")
  private val flightId = UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
  private val userId = "alice"

  private val foundOrdersForUserPact: RequestResponsePact = ConsumerPactBuilder.consumer("frontend")
    .hasPactWith("orders")

    .given("orders for user $userId")

    .uponReceiving("request for orders by userId")
    .method("GET")
    .path("/orders/$userId/")

    .willRespondWith()
    .status(200)
    .body(newJsonBody { root ->
      root.array("orders") { array ->
        array.`object` { order ->
          order.uuid("orderId", orderId)
          order.uuid("flightId", flightId)
        }
      }
    }.build())

    .toPact()

  @Test
  fun `with at least one order for user`() {
    val expected = Order(
      orderId,
      flightId
    )

    val orders: List<Order> = runWith(foundOrdersForUserPact) { mockServer ->
      val client = RestOrders(RestTemplate(), mockServer.getUrl())

      client.ordersFor(userId)
    }!!

    Assertions.assertThat(orders).containsOnly(expected)
  }
}