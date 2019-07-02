package com.lastminute.cdc.checkout.flight

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class FlightCheckoutControllerMockMvcTest {

  private val checkout = InMemoryCheckout()
  private lateinit var mockMvc: MockMvc

  @Before
  fun setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(FlightCheckoutController(checkout)).build()
  }

  @Test
  fun `with accepted request`() {
    val acceptedFlightId = UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
    val acceptedUserId = "alice"
    checkout.accept(acceptedFlightId, acceptedUserId)

    mockMvc.perform(
      post("/checkout/flight/")
        .content("""
          {
            "flightId": "$acceptedFlightId",
            "userId": "$acceptedUserId"
          }
        """.trimIndent())
        .contentType(APPLICATION_JSON_UTF8)
    )

    .andExpect(status().isAccepted)

    assertThat(checkout.wasCalledWith(acceptedFlightId, acceptedUserId)).isTrue()
  }
}