package com.lastminute.cdc.orders

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.target.TestTarget
import au.com.dius.pact.provider.spring.SpringRestPactRunner
import au.com.dius.pact.provider.spring.target.MockMvcTarget
import org.junit.runner.RunWith
import java.util.*

@RunWith(SpringRestPactRunner::class)
@Provider("orders")
@PactBroker(host = "192.168.99.100", port = "9292")
class OrdersControllerPactTest {
  private val orders = InMemoryOrders()

  @TestTarget
  @JvmField
  val target = MockMvcTarget(
    controllers = listOf(
      OrdersController(orders)
    )
  )

  @State("can create order for flight 47e2f0e6-a848-48c8-b1ab-e3dd80a80829 and alice")
  fun `can create order`() {}

  @State("orders for user alice")
  fun `orders for user alice`() {
    orders.create(UUID.randomUUID(), "alice")
    orders.create(UUID.randomUUID(), "alice")
    orders.create(UUID.randomUUID(), "alice")
  }
}