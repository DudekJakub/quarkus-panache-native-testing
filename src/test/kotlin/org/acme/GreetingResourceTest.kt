package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.acme.repositories.ExamplePostgresRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    private lateinit var repository: ExamplePostgresRepository

    @BeforeEach
    fun setUp() {
        repository = ExamplePostgresRepository()
    }

    @Test
    fun testHelloEndpoint() {

        println(repository.findAll().list().size)

        given()
          .`when`().get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello from RESTEasy Reactive"))
    }
}