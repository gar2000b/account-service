package com.onlineinteract.workflow.repository;

import java.util.ArrayList;
import java.util.Date;
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
import com.onlineinteract.workflow.domain.account.AccountEvent;
import com.onlineinteract.workflow.domain.account.AccountV1;
import com.onlineinteract.workflow.domain.account.AccountV2;
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

	public void createAccount(AccountV1 account) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		Document accountDocument = Document.parse(account.toString());
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		MongoUtility.removeEventMembers(accountDocument);
		accountsCollection.insertOne(accountDocument);
		System.out.println("Account Persisted to accounts collection");

		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountCreatedEvent");
		accountEvent.setV1(account);
		
		AccountV2 accountV2 = new AccountV2();
		accountV2.setEnabled("true");
		accountV2.setManaged("true");
		accountV2.setId("1234");
		accountV2.setName("whatever");
		accountV2.setOpeningBalance("67");
		accountV2.setSavingsRate("1.1%");
		accountV2.setType("some type");
		accountEvent.setV2(accountV2);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV1().getId().toString());
		System.out.println("AccountCreatedEvent Published to account-event-topic");
	}

	public void updateAccount(AccountV1 account) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		Document accountDocument = Document.parse(account.toString());
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		MongoUtility.removeEventMembers(accountDocument);
		accountsCollection.replaceOne(new Document("id", account.getId()), accountDocument);
		System.out.println("Account Updated in accounts collection");

		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountUpdatedEvent");
		accountEvent.setV1(account);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV1().getId().toString());
		System.out.println("AccountUpdatedEvent Published to account-event-topic");
	}

	public AccountV1 getAccount(String accountId) {
		MongoDatabase database = dbClient.getMongoClient().getDatabase("accounts");
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		BasicDBObject query = new BasicDBObject();
		query.put("id", accountId);
		FindIterable<Document> accountDocuments = accountsCollection.find(query);
		for (Document accountDocument : accountDocuments) {
			System.out.println("Found: " + accountDocument.toJson());
			MongoUtility.removeMongoId(accountDocument);
			return JsonParser.fromJson(accountDocument.toJson(), AccountV1.class);
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
