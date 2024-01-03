package org.acme

import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.KeyType.HASH
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType

internal class DynamoDbTestSetUpper {

    fun setUp() {
        createTable(DynamoDbTestResource.DYNAMO_TABLE_NAME)
    }

    fun tearDown() {
        deleteTable(DynamoDbTestResource.DYNAMO_TABLE_NAME)
    }

    private fun createTable(tableName: String) {
        val request: CreateTableRequest = CreateTableRequest.builder()
            .attributeDefinitions(
                AttributeDefinition.builder()
                    .attributeName("id")
                    .attributeType(ScalarAttributeType.S)
                    .build(),
                AttributeDefinition.builder()
                    .attributeName("sortKey")
                    .attributeType(ScalarAttributeType.S)
                    .build()
            )
            .keySchema(
                KeySchemaElement.builder()
                    .attributeName("id")
                    .keyType(HASH)
                    .build(),
                KeySchemaElement.builder()
                    .attributeName("sortKey")
                    .keyType(KeyType.RANGE)
                    .build()
            )
            .provisionedThroughput(
                ProvisionedThroughput.builder()
                    .readCapacityUnits(10)
                    .writeCapacityUnits(10)
                    .build()
            )
            .tableName(tableName)
            .build()

        DynamoDbTestResource.dynamoClient.createTable(request)
    }

    private fun deleteTable(tableName: String) {
        DynamoDbTestResource.dynamoClient.deleteTable(DeleteTableRequest.builder().tableName(tableName).build())
    }
}
