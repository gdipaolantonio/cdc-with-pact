package com.lastminute.cdc.orders

import java.util.*

class InMemoryOrders : Orders {
  private val ordersMap: MutableMap<UUID, Order> = mutableMapOf()

  override fun create(flightId: UUID, userId: String): UUID {
    val orderId = UUID.randomUUID()
    ordersMap[orderId] = Order(orderId, flightId, userId)
    return orderId
  }

  fun getBy(orderId: UUID): Order? = ordersMap[orderId]
}