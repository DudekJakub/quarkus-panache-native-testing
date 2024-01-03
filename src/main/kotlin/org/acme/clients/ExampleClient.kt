package org.acme.clients

import jakarta.inject.Singleton
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Singleton
@RegisterRestClient
@Path("/example-client")
interface ExampleClient {

    @GET
    fun getSomething(): String
}