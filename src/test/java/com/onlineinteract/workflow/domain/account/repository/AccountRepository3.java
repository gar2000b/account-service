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
import com.onlineinteract.workflow.dbclient.DbClient3;
import com.onlineinteract.workflow.domain.account.v3.AccountV3;
import com.onlineinteract.workflow.utility.JsonParser;
import com.onlineinteract.workflow.utility.MongoUtility;

@Repository
public class AccountRepository3 {

	@Autowired
	DbClient3 dbClient3;

	public AccountRepository3() {
	}

	public List<AccountV3> getAllAccountsAsList() {
		MongoDatabase database = dbClient3.getMongoClient().getDatabase(DbClient3.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		List<AccountV3> accounts = new ArrayList<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			accounts.add(JsonParser.fromJson(accountDocument.toJson(), AccountV3.class));
		}

		return accounts;
	}

	public Map<String, AccountV3> getAllAccountsAsMap() {
		MongoDatabase database = dbClient3.getMongoClient().getDatabase(DbClient3.DATABASE);
		MongoCollection<Document> accountsCollection = database.getCollection("accounts");
		FindIterable<Document> accountDocumentsIterable = accountsCollection.find();
		Map<String, AccountV3> accountsMap = new HashMap<>();
		for (Document accountDocument : accountDocumentsIterable) {
			MongoUtility.removeMongoId(accountDocument);
			AccountV3 accountV3 = JsonParser.fromJson(accountDocument.toJson(), AccountV3.class);
			accountsMap.put(accountV3.getId().toString(), accountV3);
		}

		return accountsMap;
	}
}
