package com.onlineinteract.workflow.repository;

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
import com.onlineinteract.workflow.domain.account.Account;
import com.onlineinteract.workflow.events.AccountCreatedEvent;
import com.onlineinteract.workflow.events.AccountUpdatedEvent;
import com.onlineinteract.workflow.repository.dbclient.DbClient;
import com.onlineinteract.workflow.utility.JsonParser;

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
		Document accountDocument = Document.parse(JsonParser.toJson(account));
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		accountsCollection.insertOne(accountDocument);
		System.out.println("Account Persisted to accounts collection");

		AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(account);
		accountCreatedEvent.setId(String.valueOf(accountCreatedEvent.getCreated().getTime()));
		producer.publishRecord("account-event-topic", JsonParser.toJson(accountCreatedEvent), account.getId());
		System.out.println("AccountCreatedEvent Published to account-event-topic");
	}

	public void updateAccount(Account account) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		Document accountDocument = Document.parse(JsonParser.toJson(account));
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		accountsCollection.replaceOne(new Document("id", account.getId()), accountDocument);
		System.out.println("Account Updated in accounts collection");
		
		AccountUpdatedEvent accountUpdatedEvent = new AccountUpdatedEvent(account);
		accountUpdatedEvent.setId(String.valueOf(accountUpdatedEvent.getCreated().getTime()));
		producer.publishRecord("account-event-topic", JsonParser.toJson(accountUpdatedEvent), account.getId());
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
			accountDocument.remove("_id");
			return JsonParser.fromJson(accountDocument.toJson(), Account.class);
		}

		return null;
	}

	public String getAllAccounts() {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocuments = accountsCollection.find();
		for (Document accountDocument : accountDocuments) {
			System.out.println("Removing _id from account");
			accountDocument.remove("_id");
		}
		String allAccounts = StreamSupport.stream(accountDocuments.spliterator(), false).map(Document::toJson)
				.collect(Collectors.joining(", ", "[", "]"));

		return allAccounts;
	}
}
