package com.lastminute.cdc.orders

import java.util.*

class InMemoryOrders : Orders {
  private val ordersList: MutableList<Order> = mutableListOf()

  override fun create(flightId: UUID, userId: String): UUID {
    val orderId = UUID.randomUUID()
    ordersList.add(Order(orderId, flightId, userId))
    return orderId
  }

  fun getBy(orderId: UUID): Order? = ordersList.find { order -> order.orderId == orderId }
}