package com.lastminute.cdc.orders

import org.assertj.core.api.Assertions
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
}