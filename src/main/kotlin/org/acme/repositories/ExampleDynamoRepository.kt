package org.acme.repositories

import jakarta.enterprise.context.ApplicationScoped
import org.acme.dynamo.DynamoDbClient
import org.acme.models.MyDynamoEntity

@ApplicationScoped
class ExampleDynamoRepository(
    private val client: DynamoDbClient
) {

    fun saveEntity(entity: MyDynamoEntity): MyDynamoEntity {
        return client.updateItem(entity)
    }

    fun findById(id: String, sortKey: String): MyDynamoEntity {
        return client.findById(id, sortKey)
    }
}