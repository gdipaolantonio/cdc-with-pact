package com.lastminute.cdc.frontend

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.PactTestRun
import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.runConsumerTest
import au.com.dius.pact.model.MockProviderConfig
import au.com.dius.pact.model.RequestResponsePact

fun <T> runWith(pact: RequestResponsePact, test: (MockServer) -> T?): T? {
  var result: T? = null
  val pactVerificationResult = runConsumerTest(pact, MockProviderConfig.createDefault(), object : PactTestRun {
    override fun run(mockServer: MockServer) {
      result = test.invoke(mockServer)
    }
  })

  return when (pactVerificationResult) {
    is PactVerificationResult.Ok -> result
    else -> throw AssertionError(pactVerificationResult.toString())
  }
}