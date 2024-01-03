package org.acme.models

import io.quarkus.runtime.annotations.RegisterForReflection
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
@RegisterForReflection
data class MyDynamoEntity(

    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute(value = "id")
    var id: String,

    @get:DynamoDbAttribute(value = "firstname")
    var firstname: String,

    @get:DynamoDbAttribute(value = "surname")
    var surname: String,

    @get:DynamoDbSortKey
    @get:DynamoDbAttribute("sortKey")
    var sortKey: String? = null
)