package org.acme.dynamo

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

abstract class DynamoDbClientBase<T>(
    dynamoClient: DynamoDbEnhancedClient,
    tableName: String,
    clazz: Class<T>
) {

    private val schema: TableSchema<T> = TableSchema.fromClass(clazz)
    private val table: DynamoDbTable<T> = dynamoClient.table(tableName, schema)

    fun updateItem(item: T): T {
        return table.updateItem(item)
    }

    fun findById(id: String, sortKey: String): T {
        val searchKey = Key.builder().partitionValue(id).sortValue(sortKey).build()
        return table.getItem(searchKey)
    }
}