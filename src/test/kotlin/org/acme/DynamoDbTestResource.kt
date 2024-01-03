package org.acme

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Testcontainers
internal class DynamoDbTestResource : QuarkusTestResourceLifecycleManager {

    companion object {
        const val DYNAMO_TABLE_NAME = "test-table-name"

        private val localStackImage = DockerImageName.parse("localstack/localstack:1.4.0")

        @Container
        val localStack: LocalStackContainer = LocalStackContainer(localStackImage)
            .withServices(DYNAMODB)
            .withEnv("AWS_ACCESS_KEY_ID", "accesskey")
            .withEnv("AWS_SECRET_ACCESS_KEY", "secretkey")

        val dynamoClient: DynamoDbClient by lazy {
            DynamoDbClient.builder()
                .endpointOverride(localStack.getEndpointOverride(DYNAMODB))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                            localStack.accessKey,
                            localStack.secretKey
                        )
                    )
                )
                .region(Region.of(localStack.region))
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build()
        }
    }

    override fun start(): MutableMap<String, String> {
        localStack.start()
        return mutableMapOf(
            "dynamodb-name" to DYNAMO_TABLE_NAME,
            "quarkus.dynamodb.endpoint-override" to localStack.getEndpointOverride(DYNAMODB).toString()
        )
    }

    override fun stop() {
        localStack.stop()
    }
}
