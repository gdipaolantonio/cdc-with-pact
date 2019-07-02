package com.lastminute.cdc.orders

import com.fasterxml.jackson.databind.ObjectMapper
import com.lastminute.cdc.orders.OrdersController.OrdersJsonResponse
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.auto.Mock
import org.jmock.integration.junit4.JUnitRuleMockery
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
      post("/orders/")
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

  @Test
  fun `load orders for user`() {
    val userId = "alice"
    val expectedOrdersList = listOf(
      Order(UUID.randomUUID(), UUID.randomUUID(), userId),
      Order(UUID.randomUUID(), UUID.randomUUID(), userId),
      Order(UUID.randomUUID(), UUID.randomUUID(), userId)
    )

    context.checking(Expectations().apply {
      allowing(orders).getBy(userId)
      will(returnValue(expectedOrdersList))
    })

    mockMvc.perform(get("/orders/{userId}/", userId))

    .andExpect(status().isOk)
    .andExpect(content().json(toJson(OrdersJsonResponse(expectedOrdersList))))
  }

  private fun toJson(any: Any): String = ObjectMapper().writeValueAsString(any)
}