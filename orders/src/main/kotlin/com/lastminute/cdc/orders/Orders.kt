package com.lastminute.cdc.orders

import java.util.*

interface Orders {
  fun create(flightId: UUID, userId: String): UUID
}