package com.lastminute.cdc.checkout.flight

import java.lang.RuntimeException
import java.util.*

class CheckoutException(flightId: UUID, userId: String):
  RuntimeException("Cannot checkout flight with id $flightId for user $userId")
