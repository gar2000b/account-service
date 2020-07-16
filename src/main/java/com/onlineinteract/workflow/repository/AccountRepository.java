package com.onlineinteract.workflow.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.onlineinteract.workflow.bus.Producer;
import com.onlineinteract.workflow.domain.account.v1.Account;
import com.onlineinteract.workflow.repository.dbclient.DbClient;
import com.onlineinteract.workflow.utility.JsonParser;
import com.onlineinteract.workflow.utility.MongoUtility;

@Repository
public class AccountRepository {

	@Autowired
	DbClient dbClient;

	@Autowired
	private Producer producer;

	public AccountRepository() {
	}

	public void createAccount(Account account) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		Document accountDocument = Document.parse(account.toString());
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		MongoUtility.removeEventMembers(accountDocument);
		accountsCollection.insertOne(accountDocument);
		System.out.println("Account Persisted to accounts collection");

		account.setEventId(String.valueOf(account.getCreated()));
		account.setEventType("AccountCreatedEvent");

		producer.publishRecord("account-event-topic", account, account.getId().toString());
		System.out.println("AccountCreatedEvent Published to account-event-topic");
	}

	public void updateAccount(Account account) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		Document accountDocument = Document.parse(account.toString());
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		MongoUtility.removeEventMembers(accountDocument);
		accountsCollection.replaceOne(new Document("id", account.getId()), accountDocument);
		System.out.println("Account Updated in accounts collection");
		
		account.setEventId(String.valueOf(account.getCreated()));
		account.setEventType("AccountUpdatedEvent");

		producer.publishRecord("account-event-topic", account, account.getId().toString());
		System.out.println("AccountUpdatedEvent Published to account-event-topic");
	}

	public Account getAccount(String accountId) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		BasicDBObject query = new BasicDBObject();
		query.put("id", accountId);
		FindIterable<Document> accountDocuments = accountsCollection.find(query);
		for (Document accountDocument : accountDocuments) {
			System.out.println("Found: " + accountDocument.toJson());
			MongoUtility.removeMongoId(accountDocument);
			return JsonParser.fromJson(accountDocument.toJson(), Account.class);
		}

		return null;
	}

	public String getAllAccounts() {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		List<Document> accountDocuments = new ArrayList<>();
		for (Document accountDocument : accountDocumentsIterable) {
			System.out.println("Removing _id from account");
			accountDocument.remove("_id");
			accountDocuments.add(accountDocument);
		}
		String allAccounts = StreamSupport.stream(accountDocuments.spliterator(), false).map(Document::toJson)
				.collect(Collectors.joining(", ", "[", "]"));

		return allAccounts;
	}
}
