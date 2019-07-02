package com.lastminute.cdc.orders

import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class OrdersControllerMockMvcTest {
  private lateinit var mockMvc: MockMvc

  @get:Rule
  val context = JUnitRuleMockery()

  @Mock
  private lateinit var orders : Orders

  @Before
  fun setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(OrdersController(orders)).build()
  }

  @Test
  fun createOrderSuccessfully() {
    val flightId = UUID.fromString("47e2f0e6-a848-48c8-b1ab-e3dd80a80829")
    val userId = "alice"

    context.checking(Expectations().apply {
      oneOf(orders).create(flightId, userId)
    })

    mockMvc.perform(
      post("/orders/flight/")
        .contentType(APPLICATION_JSON_UTF8)
        .content("""
          {
            "flightId": "$flightId",
            "userId": "$userId"
          }
        """.trimIndent())
    )
    .andExpect(status().isCreated)
  }
}