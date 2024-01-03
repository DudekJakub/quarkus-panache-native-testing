package org.acme.controllers

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.models.MyDynamoEntity
import org.acme.repositories.ExampleDynamoRepository

@Path("/hello")
class GreetingResource(
    private val dynamoRepository: ExampleDynamoRepository
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        println("TEST DYNAMO REPOSITORY SAVE START")
        println(dynamoRepository.saveEntity(
            MyDynamoEntity("1", "Jackob", "Dudere", "SomeSortKeyValue")
        ))
        println("TEST DYNAMO REPOSITORY SAVE END\n")

        println("TEST DYNAMO REPOSITORY FIND_BY_ID START")
        println(dynamoRepository.findById("1", "SomeSortKeyValue"))
        println("TEST DYNAMO REPOSITORY FIND_BY_ID END\n")

        return "Hello from RESTEasy Reactive"
    }
}