# Software Registry Project - Indexer

This project implements source code indexing using Apache Lucene. The analyzed files have been previously downloaded by the `Jenkins Software Registry Scanner` pipeline.

## Build and Run

### Configuration

Before running the application it is necessary to configure the following files:

- `src/main/resources/application.properties`

### Run the application

It is recommended to execute the following steps in order to regenerate Lucene Indexes:

1. Remove the index directory previously created
2. Run the java program:
   - java -jar `APP`.jar
3. Restart the backend docker container

Example:

```bash
rm -rf /app/software-registry/index
java -jar /app/software-registry/lucene-indexer.jar
docker restart software-registry-backend:2.1.0
```
