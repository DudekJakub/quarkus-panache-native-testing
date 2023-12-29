package org.acme.controllers

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.repositories.ExamplePostgresRepository

@Path("/hello")
class GreetingResource(
    private val repository: ExamplePostgresRepository
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        println(repository.findById(1L))
        return "Hello from RESTEasy Reactive"
    }
}