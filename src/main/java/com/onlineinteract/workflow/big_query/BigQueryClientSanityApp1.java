package com.onlineinteract.workflow.big_query;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BigQueryClientSanityApp1 {

    private static final String PROJECT_ID = "bigquery-demo-377717";
    private static final String DATASET_ID = "dataset1";
    private static final String TABLE_ID = "names";

    public static void main(String[] args) throws IOException, InterruptedException {

        // Set the path to the service account key file
        String pathToCredentials = "bigquery-demo-377717-0f9015abd04f.json";

        // Authenticate with BigQuery using a service account
        InputStream credentialsStream = BigQueryClientSanityApp1.class.getResourceAsStream("/bigquery-demo-377717-0f9015abd04f.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        BigQuery bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).setProjectId(PROJECT_ID).build().getService();

        // Construct the SQL query to fetch all records from the table
        String query = String.format("SELECT * FROM `%s.%s.%s`", PROJECT_ID, DATASET_ID, TABLE_ID);

        // Create a job configuration for the query
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        // Execute the query and get the results
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        queryJob = queryJob.waitFor();
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        TableResult result = queryJob.getQueryResults();

        // Print the results
        for (FieldValueList row : result.iterateAll()) {
            System.out.printf("%s\n", row.get("name").getStringValue());
        }
    }
}
