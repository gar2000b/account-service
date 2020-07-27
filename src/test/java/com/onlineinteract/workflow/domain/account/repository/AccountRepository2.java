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
import com.onlineinteract.workflow.dbclient.DbClient2;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.utility.JsonParser;
import com.onlineinteract.workflow.utility.MongoUtility;

@Repository
public class AccountRepository2 {

	@Autowired
	DbClient2 dbClient2;

	public AccountRepository2() {
	}

	public List<AccountV2> getAllAccountsAsList() {
		MongoDatabase database = dbClient2.getMongoClient().getDatabase(DbClient2.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		List<AccountV2> accounts = new ArrayList<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			accounts.add(JsonParser.fromJson(accountDocument.toJson(), AccountV2.class));
		}

		return accounts;
	}

	public Map<String, AccountV2> getAllAccountsAsMap() {
		MongoDatabase database = dbClient2.getMongoClient().getDatabase(DbClient2.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		Map<String, AccountV2> accountsMap = new HashMap<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			AccountV2 accountV2 = JsonParser.fromJson(accountDocument.toJson(), AccountV2.class);
			accountsMap.put(accountV2.getId().toString(), accountV2);
		}

		return accountsMap;
	}
}
