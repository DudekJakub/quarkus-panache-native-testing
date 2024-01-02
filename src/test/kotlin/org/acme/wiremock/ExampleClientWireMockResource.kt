package org.acme.wiremock

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

internal class ExampleClientWireMockResource : WireMockResource() {

    override fun start(): MutableMap<String, String> {
        val appProperties = super.start()

        stubFor(
            get(urlPathMatching("/example-client"))
                .willReturn(ok().withBody("Hello from ExampleClient"))
        )

        appProperties["quarkus.rest-client.\"org.acme.client.ExampleClient\".url"] = server.baseUrl()
        return appProperties
    }
}