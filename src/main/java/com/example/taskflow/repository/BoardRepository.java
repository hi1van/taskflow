package com.example.taskflow.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.taskflow.model.Board;

@Repository
public class BoardRepository {
    private final DynamoDbClient client;
    private static final String TABLE = "Boards";

    public BoardRepository(DynamoDbClient client) {
        this.client = client;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(TABLE).build());
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(TABLE)
                    .keySchema(KeySchemaElement.builder().attributeName("boardId").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("boardId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build());
        }
    }

    public void saveUser(Board board) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("boardId", AttributeValue.builder().s(board.getBoardId()).build());
        item.put("name", AttributeValue.builder().s(board.getName()).build());
        item.put("description", AttributeValue.builder().s(board.getDescription()).build());
        item.put("ownerId", AttributeValue.builder().s(board.getOwnerId()).build());
        item.put("members", AttributeValue.builder()
                .ss(board.getMembers())
                .build());
        item.put("createdAt", AttributeValue.builder().s(board.getCreatedAt().toString()).build());

        client.putItem(PutItemRequest.builder().tableName(TABLE).item(item).build());
    }
}
