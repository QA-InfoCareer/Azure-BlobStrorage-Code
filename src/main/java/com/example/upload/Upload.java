package com.example.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.azure.core.http.HttpResponse;
import com.azure.core.http.rest.Response;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class Upload {

	public static void main(String[] args) {

		String connectionString = "DefaultEndpointsProtocol=https;AccountName=samplestorageblob;AccountKey=tQ6eF7tffZ5GvYkXaTGtVugZOZK3ubm2vpVjraTYGKm2mN9iU9uw0kpedihHEaY7zbPk5ToqlVon+AStYnnoyQ==;EndpointSuffix=core.windows.net";

		String containerName = "fileupload";

		// String blobName = "";

		String filePath = "D:\\BasePage.txt";

		try {

			// Create a BlobServiceClient using the connection string
			BlobServiceClientBuilder serviceClientBuilder = new BlobServiceClientBuilder()

					.connectionString(connectionString);

			var blobServiceClient = serviceClientBuilder.buildClient();

			// Check if the container exists, create it if not
			if (!blobServiceClient.getBlobContainerClient(containerName).exists()) {

				blobServiceClient.createBlobContainer(containerName);

				System.out.println("Container created: " + containerName);
			}

			// Generate a unique blob name using a timestamp and the original file name
			String originalFileName = new File(filePath).getName();

			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

			String generatedBlobName = timestamp + "_" + originalFileName;

			System.out.println("The Generated blob name is : " + generatedBlobName);

			// Create a BlobClient for the container and blob
			 var blobClient = new BlobClientBuilder().connectionString(connectionString)
                     .containerName(containerName)
                     .blobName(generatedBlobName)
                     .buildClient();

			// Upload the file

			FileInputStream fileInputStream = new FileInputStream(new File(filePath));

			blobClient.upload(fileInputStream, new File(filePath).length(), true);

			System.out.println("File uploaded successfully.");

		} catch (IOException e) {

			e.printStackTrace();

			System.out.println(e.getMessage());
		}

	}

}
