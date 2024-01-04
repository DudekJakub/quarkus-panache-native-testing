package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.acme.models.MyDynamoEntity
import org.eclipse.microprofile.config.ConfigProvider
import org.junit.jupiter.api.Test
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import java.net.URI
import java.util.stream.Collectors

@QuarkusTest
class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        val port = ConfigProvider.getConfig().getValue("quarkus.dynamodb.endpoint-override", String::class.java)

        val dynamoClient = DynamoDbClient.builder()
            .endpointOverride(URI.create(port))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        "test-key",
                        "test-secret"
                    )
                )
            )
            .region(Region.EU_CENTRAL_1)
            .httpClient(UrlConnectionHttpClient.builder().build())
            .build()

        dynamoClient.createTable(createTable("test-table-name"))

        val enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoClient)
            .build()

        val table: DynamoDbTable<MyDynamoEntity> = enhancedClient.table("test-table-name", TableSchema.fromBean(MyDynamoEntity::class.java))

        val item = table.getItem(Key.builder().partitionValue("test").sortValue("test").build())
        println("Item = $item")

        val allItems = table.scan().items().stream().collect(Collectors.toList())
        println("AllItems = $allItems")

//        given()
//            .`when`().get("/hello")
//            .then()
//            .statusCode(200)
//            .body(`is`("Hello from RESTEasy Reactive"))
    }

    private fun createTable(tableName: String): CreateTableRequest {
        return CreateTableRequest.builder()
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
                    .keyType(KeyType.HASH)
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
    }
}