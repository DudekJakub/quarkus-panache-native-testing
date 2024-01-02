package org.acme

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import io.quarkiverse.wiremock.devservice.ConnectWireMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.eclipse.microprofile.config.ConfigProvider
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
@ConnectWireMock
class GreetingResourceTest {

    private lateinit var wiremock: WireMock

    @Test
    fun testHelloEndpoint() {
        ConfigProvider.getConfig().configSources.forEach {
            it.properties.forEach { prop -> println(prop) }
        }

        wiremock.register(
            get(urlPathMatching("/example-client"))
                .willReturn(aResponse().withStatus(200).withBody("Hello from ExampleClient"))
        )

        wiremock.allStubMappings().mappings.forEach { println(it) }

        given()
            .`when`()[String.format("http://localhost:%d/example-client", 8089)]
            .then().statusCode(200)
            .body(`is`("Hello from ExampleClient"))

        given()
            .`when`().get("/hello")
            .then()
            .statusCode(200)
            .body(`is`("Hello from RESTEasy Reactive"))
    }
}