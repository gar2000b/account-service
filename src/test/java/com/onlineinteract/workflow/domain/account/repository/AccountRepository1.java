package com.onlineinteract.workflow.domain.account.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.onlineinteract.workflow.dbclient.DbClient1;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.utility.JsonParser;
import com.onlineinteract.workflow.utility.MongoUtility;

@Repository
public class AccountRepository1 {

	@Autowired
	DbClient1 dbClient1;

	public AccountRepository1() {
	}

	public List<AccountV1> getAllAccountsAsList() {
		MongoDatabase database = dbClient1.getMongoClient().getDatabase(DbClient1.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		List<AccountV1> accounts = new ArrayList<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			accounts.add(JsonParser.fromJson(accountDocument.toJson(), AccountV1.class));
		}

		return accounts;
	}

	public Map<String, AccountV1> getAllAccountsAsMap() {
		MongoDatabase database = dbClient1.getMongoClient().getDatabase(DbClient1.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		Map<String, AccountV1> accountsMap = new HashMap<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			AccountV1 accountV1 = JsonParser.fromJson(accountDocument.toJson(), AccountV1.class);
			accountsMap.put(accountV1.getId().toString(), accountV1);
		}

		return accountsMap;
	}
}
