package org.acme.controllers

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.client.ExampleClient
import org.eclipse.microprofile.rest.client.inject.RestClient

@Path("/hello")
class GreetingResource(
    @RestClient private val client: ExampleClient
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        println("\nTEST REST CLIENT WIREMOCK START")
        println(client.getSomething())
        println("TEST REST CLIENT WIREMOCK END\n")

        return "Hello from RESTEasy Reactive"
    }
}