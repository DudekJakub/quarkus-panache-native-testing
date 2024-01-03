package org.acme.dynamo

import jakarta.inject.Singleton
import org.acme.models.MyDynamoEntity
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient

@Singleton
class DynamoDbClient(
    @ConfigProperty(name = "dynamodb-name") tableName: String,
    dynamoClient: DynamoDbEnhancedClient
) : DynamoDbClientBase<MyDynamoEntity>(
    dynamoClient = dynamoClient,
    tableName = tableName,
    clazz = MyDynamoEntity::class.java
)