package com.example.taskflow.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

import com.example.taskflow.model.User;

@Repository
public class UserRepository {
    private final DynamoDbClient client;
    private static final String TABLE = "Users";

    public UserRepository(DynamoDbClient client) {
        this.client = client;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(TABLE).build());
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(TABLE)
                    .keySchema(KeySchemaElement.builder().attributeName("userId").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("userId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build());
        }
    }

    public void saveUser(User user) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.builder().s(user.getUserId()).build());
        item.put("name", AttributeValue.builder().s(user.getName()).build());
        item.put("email", AttributeValue.builder().s(user.getEmail()).build());
        item.put("createdAt", AttributeValue.builder().s(user.getCreatedAt().toString()).build());

        client.putItem(PutItemRequest.builder().tableName(TABLE).item(item).build());
    }
}

