package com.example.taskflow.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.taskflow.model.Task;

@Repository
public class TaskRepository {
    private final DynamoDbClient client;
    private static final String TABLE = "Tasks";

    public TaskRepository(DynamoDbClient client) {
        this.client = client;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(TABLE).build());
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(TABLE)
                    .keySchema(KeySchemaElement.builder().attributeName("taskId").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("taskId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build());
        }
    }

    public void saveUser(Task task) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("taskId", AttributeValue.builder().s(task.getTaskId()).build());
        item.put("boardId", AttributeValue.builder().s(task.getBoardId()).build());
        item.put("title", AttributeValue.builder().s(task.getTitle()).build());
        item.put("description", AttributeValue.builder().s(task.getDescription()).build());
        item.put("status", AttributeValue.builder().s(task.getStatus()).build());
        item.put("assigneeId", AttributeValue.builder().s(task.getAssigneeId()).build());
        item.put("createdAt", AttributeValue.builder().s(task.getCreatedAt().toString()).build());
        item.put("updatedAt", AttributeValue.builder().s(task.getUpdatedAt().toString()).build());

        client.putItem(PutItemRequest.builder().tableName(TABLE).item(item).build());
    }
}
