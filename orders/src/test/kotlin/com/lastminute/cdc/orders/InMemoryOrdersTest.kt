package com.lastminute.cdc.orders

import org.assertj.core.api.Assertions.*
import org.junit.Test
import java.util.*

class InMemoryOrdersTest {
  @Test
  fun `create order successfully`() {
    val orders = InMemoryOrders()

    val flightId = UUID.randomUUID()
    val userId = "alice"

    val orderId = orders.create(flightId, userId)

    val expected = Order(orderId, flightId, userId)
    assertThat(orders.getBy(orderId)).isEqualTo(expected)
  }

  @Test
  fun `get orders by userId`() {
    val orders = InMemoryOrders()

    orders.create(UUID.randomUUID(), "alice")

    val firstFlightIdForBob = UUID.randomUUID()
    val secondFlightIdForBob = UUID.randomUUID()
    val firstOrderIdForBob = orders.create(firstFlightIdForBob, "bob")
    val secondOrderIdForBob = orders.create(secondFlightIdForBob, "bob")

    val ordersList = orders.getBy("bob")

    val firstOrderForBob = Order(firstOrderIdForBob, firstFlightIdForBob, "bob")
    val secondOrderForBob = Order(secondOrderIdForBob, secondFlightIdForBob, "bob")
    assertThat(ordersList).containsExactlyInAnyOrder(firstOrderForBob, secondOrderForBob)
  }
}