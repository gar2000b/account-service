package com.onlineinteract.workflow;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.onlineinteract.workflow.domain.account.bus.DataFixEventGenerator;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository1;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository2;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository3;
import com.onlineinteract.workflow.domain.account.repository.AccountRepositorySource;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.domain.account.v3.AccountV3;
import com.onlineinteract.workflow.domain.account.v3.Clone;
import com.onlineinteract.workflow.utility.JsonParser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class AccountDomainTests {

	static List<String> prefixes = Arrays.asList(new String[] { "Everyday", "Super Saver", "Mega Saver" });
	static List<String> names = Arrays.asList(new String[] { "Gary", "Jennifer", "Farzad", "Jane", "Craig", "Hazel",
			"Basil", "Mika", "Bryan", "Claire", "James", "Andrea", "Jorge", "Gill", "Slade", "Jason", "Fredericka",
			"Melanie", "Todd", "Rama", "Lyle", "Austin", "Jocelyn", "Mohammad", "Cedric", "Kelsey", "Simon", "Ralph",
			"Yoshi", "Zeus", "Dustin", "Hiram", "Rae", "Tyrone", "Fredericka", "Serina", "Porter", "Xavier", "Nissim",
			"Jacob", "Phoebe", "Adam", "Uma", "Hector", "Callum", "Clark", "Avye", "Dara", "Deacon", "Hillary" });
	static List<String> types = Arrays.asList(new String[] { "chequing", "saving" });
	static List<String> savingsRates = Arrays.asList(new String[] { "2.73%", "5.63%", "5.93%", "6.93%" });
	static List<String> accounts = new ArrayList<>();

	static volatile int count = 0;
	static volatile int noOfAccounts = 0;
	static volatile int createsCount = 0;
	static volatile int updatesCount = 0;
	static volatile int position = 1;
	static volatile long end;
	static volatile long start;
	static volatile long diff;
	static volatile long tps;

	@Autowired
	AccountRepositorySource accountRepositorySource;

	@Autowired
	AccountRepository1 accountRepository1;

	@Autowired
	AccountRepository2 accountRepository2;

	@Autowired
	AccountRepository3 accountRepository3;

	@Autowired
	DataFixEventGenerator dataFixEventGenerator;

	static final int NO_OF_THREADS = 25;
	static int totalNoOfTransactions = 50000;
	private Map<String, AccountV3> accountsCreated = new HashMap<>();
	List<String> accountsCreatedKeyList = new ArrayList<String>();
	private Map<String, Long> accountsCreatedTime = new HashMap<>();
	private Map<String, Long> accountsUpdatedTime = new HashMap<>();

	public static void main(String[] args) throws InterruptedException {
		LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.OFF);
		AccountDomainTests accountDomainTests = new AccountDomainTests();
		accountDomainTests.injectLoad();
	}
	
	private void injectLoad() throws InterruptedException {
		System.out.println("Injecting load");
		ExecutorService executor = Executors.newFixedThreadPool(NO_OF_THREADS);
		count = 0;
		start = System.currentTimeMillis();
		end = System.currentTimeMillis();

		for (int i = 0; i < NO_OF_THREADS; i++) {
			executor.submit(new Thread(() -> {
				while (count < totalNoOfTransactions) {
					try {
						randomCommand();
						count++;
						if (count % 1000 == 0) {
							end = System.currentTimeMillis();
							diff = end - start;
							start = end;
							tps = (long) (1000 / (diff / 1000d));
							System.out.println("Time every 1000 records: " + diff + "ms with a tps: " + tps);
						}
					} catch (Error e) {
						System.out.println("Thread caught an exception");
					}
				}
			}));
		}
		executor.shutdown();
		executor.awaitTermination(12, TimeUnit.HOURS);
		System.out.println("All account processing threads should have completed, presenting summary in 2 secs...");
		Thread.sleep(2000);

		System.out.println("\nSummary:");
		System.out.println("--------");
		System.out.println();
		System.out.println("No of accounts: " + noOfAccounts);
		System.out.println("Total transaction count: " + count);
		System.out.println("Total creation count: " + createsCount);
		System.out.println("Total update count: " + updatesCount);
	}

	@Test
	@Ignore
	public void applyV2ToV3DataFix() {
		List<AccountV2> accountsV2 = accountRepositorySource.getAllV2AccountsAsList();
		for (AccountV2 accountV2 : accountsV2) {
			if (accountV2.getAddr1() == null || accountV2.getAddr2() == null)
				continue;
			accountV2.setAddr1(accountV2.getAddr1() + " " + accountV2.getAddr2());
			accountV2.setAddr2("");
			try {
				dataFixEventGenerator.updateAccount(accountV2);
				AccountV3 accountV3 = Clone.cloneAccountV3FromV2(accountV2);
				accountRepositorySource.updateAccount(accountV3);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void reconciliationTestSourceAgainstV1() {
		List<AccountV3> accountsSource = accountRepositorySource.getAllV3AccountsAsList();
		Map<String, AccountV1> accountsV1 = accountRepository1.getAllAccountsAsMap();
		int count = 0;

		assertEquals(accountsSource.size(), accountsV1.size());
		for (AccountV3 accountSource : accountsSource) {
			count++;
			AccountV1 accountV1 = accountsV1.get(accountSource.getId());
			if (!accountSource.getOpeningBalance().equals(accountV1.getOpeningBalance())) {
				System.out.println(
						"Opening Balance is not equal - expected: " + accountSource.getOpeningBalance() + " - was: "
								+ accountV1.getOpeningBalance() + " - count: " + count + " - id: " + accountV1.getId());
			}
			assertEquals(accountSource.getName(), accountV1.getName());
			assertEquals(accountSource.getType(), accountV1.getType());
			assertEquals(accountSource.getOpeningBalance(), accountV1.getOpeningBalance());
			assertEquals(accountSource.getSavingsRate(), accountV1.getSavingsRate());
		}
	}

	@Test
	public void reconciliationTestSourceAgainstV2() {
		List<AccountV3> accountsSource = accountRepositorySource.getAllV3AccountsAsList();
		Map<String, AccountV2> accountsV2 = accountRepository2.getAllAccountsAsMap();

		assertEquals(accountsSource.size(), accountsV2.size());
		for (AccountV3 accountSource : accountsSource) {
			AccountV2 accountV2 = accountsV2.get(accountSource.getId());
			assertEquals(accountSource.getName(), accountV2.getName());
			assertEquals(accountSource.getType(), accountV2.getType());
			assertEquals(accountSource.getOpeningBalance(), accountV2.getOpeningBalance());
			assertEquals(accountSource.getSavingsRate(), accountV2.getSavingsRate());
			assertEquals(accountSource.getEnabled(), accountV2.getEnabled());
			assertEquals(accountSource.getAddr(), accountV2.getAddr1());
		}
	}

	@Test
	public void reconciliationTestSourceAgainstV3() {
		List<AccountV3> accountsSource = accountRepositorySource.getAllV3AccountsAsList();
		Map<String, AccountV3> accountsV3 = accountRepository3.getAllAccountsAsMap();

		assertEquals(accountsSource.size(), accountsV3.size());
		for (AccountV3 accountSource : accountsSource) {
			AccountV3 accountV3 = accountsV3.get(accountSource.getId());
//			if (!accountSource.getOpeningBalance().equals(accountV3.getOpeningBalance())) {
//				System.out.println(
//						"Opening Balance is not equal - expected: " + accountSource.getOpeningBalance() + " - was: "
//								+ accountV3.getOpeningBalance() + " - count: " + count + " - id: " + accountV3.getId());
//			}
			assertEquals(accountSource.getName(), accountV3.getName());
			assertEquals(accountSource.getType(), accountV3.getType());
			assertEquals(accountSource.getOpeningBalance(), accountV3.getOpeningBalance());
			assertEquals(accountSource.getSavingsRate(), accountV3.getSavingsRate());
			assertEquals(accountSource.getEnabled(), accountV3.getEnabled());
			assertEquals(accountSource.getAddr(), accountV3.getAddr());
		}
	}

	private void randomCommand() {
		if (count % 4 == 0) {
			createAccount(generateAccountJsonV3());
		} else {
			updateRandomAccount();
		}
	}

	private void updateRandomAccount() {
		ThreadLocal<Integer> accountsIndex = ThreadLocal.withInitial(() -> fetchNextAccountIndex());
		ThreadLocal<AccountV3> accountV3 = ThreadLocal
				.withInitial(() -> accountsCreated.get(accountsCreatedKeyList.get(accountsIndex.get())));
		ThreadLocal<Long> timeAccountV3Created = ThreadLocal
				.withInitial(() -> accountsCreatedTime.get(accountsCreatedKeyList.get(accountsIndex.get())));
		ThreadLocal<Long> timeAccountV3Updated = ThreadLocal
				.withInitial(() -> accountsUpdatedTime.get(accountsCreatedKeyList.get(accountsIndex.get())));

		if (timeAccountV3Updated.get() == null)
			timeAccountV3Updated.set(0L);

		ThreadLocal<Long> diffCreated = ThreadLocal
				.withInitial(() -> System.currentTimeMillis() - timeAccountV3Created.get());
		ThreadLocal<Long> diffUpdated = ThreadLocal
				.withInitial(() -> System.currentTimeMillis() - timeAccountV3Updated.get());
		
		if (diffCreated.get() < 2000 || diffUpdated.get() < 2000) {
			count--;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		accountV3.get().setOpeningBalance("$" + ThreadLocalRandom.current().nextInt(400, 1001));
		ThreadLocal<String> accountJson = ThreadLocal.withInitial(() -> accountV3ToJson(accountV3.get()));

		String accountServiceUrl = "http://colossal.canadacentral.cloudapp.azure.com:9087/account";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ThreadLocal<HttpEntity<String>> request = ThreadLocal
				.withInitial(() -> new HttpEntity<String>(accountJson.get(), headers));
		ResponseEntity<String> response = restTemplate.exchange(accountServiceUrl, HttpMethod.PUT, request.get(),
				String.class);

		if (response.getStatusCode().value() == 200) {
			accountsUpdatedTime.put(accountV3.get().getId().toString(), System.currentTimeMillis());
			updatesCount++;
		} else {
			System.out.println("Response back: " + response.getStatusCode().value());
		}
	}

	private synchronized int fetchNextAccountIndex() {
		int accountsIndex = 0;
		try {
			if (position == 1)
				accountsIndex = ThreadLocalRandom.current().nextInt(0, (accountsCreatedKeyList.size() / 4));
			if (position == 2)
				accountsIndex = ThreadLocalRandom.current().nextInt((accountsCreatedKeyList.size() / 4),
						(accountsCreatedKeyList.size() / 2));
			if (position == 3)
				accountsIndex = ThreadLocalRandom.current().nextInt((accountsCreatedKeyList.size() / 2),
						(int) (accountsCreatedKeyList.size() * 0.75));
			if (position == 4)
				accountsIndex = ThreadLocalRandom.current().nextInt((int) (accountsCreatedKeyList.size() * 0.75),
						accountsCreatedKeyList.size());
		} catch (Error e) {
			System.out.println("Error, accountsCreatedKeyList.size(): " + accountsCreatedKeyList.size()
					+ " - position: " + position);
		}

		if (position == 4)
			position = 1;
		else
			position++;

		return accountsIndex;
	}

	private synchronized String accountV3ToJson(AccountV3 accountV3) {
		String accountV3Json = "{\"id\": \"" + accountV3.getId() + "\", \"name\": \"" + accountV3.getName()
				+ "\", \"type\": \"" + accountV3.getSavingsRate() + "\", \"openingBalance\": \""
				+ accountV3.getOpeningBalance() + "\", \"savingsRate\": \"" + accountV3.getSavingsRate()
				+ "\", \"enabled\": " + accountV3.getEnabled() + ", \"addr\": \"" + accountV3.getAddr() + "\"}";
		return accountV3Json;
	}

	private synchronized void createAccount(String accountJson) {
		String accountServiceUrl = "http://colossal.canadacentral.cloudapp.azure.com:9087/account";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(accountJson, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(accountServiceUrl, request, String.class);

		if (response.getStatusCode().value() == 200) {
			String accountV3Response = response.getBody().replace("createAccount(): ", "");
			AccountV3 accountV3 = JsonParser.fromJson(accountV3Response, AccountV3.class);
			accountsCreated.put(accountV3.getId().toString(), accountV3);
			accountsCreatedTime.put(accountV3.getId().toString(), System.currentTimeMillis());
			accountsCreatedKeyList.add(accountV3.getId().toString());
			createsCount++;
		} else {
			System.out.println("Response back: " + response.getStatusCode().value());
		}
	}

	private synchronized String generateAccountJsonV3() {
		int prefixIndex = ThreadLocalRandom.current().nextInt(0, prefixes.size());
		String prefix = prefixes.get(prefixIndex);
		int nameIndex = ThreadLocalRandom.current().nextInt(0, names.size());
		String name = names.get(nameIndex);
		int typeIndex = ThreadLocalRandom.current().nextInt(0, types.size());
		String type = types.get(typeIndex);
		String openingBalance = "$" + ThreadLocalRandom.current().nextInt(400, 1001);
		int savingsRateIndex = ThreadLocalRandom.current().nextInt(0, savingsRates.size());
		String savingsRate = savingsRates.get(savingsRateIndex);
		boolean enabled = new Random().nextBoolean();
		String addr = String.valueOf(ThreadLocalRandom.current().nextInt(1000000, 10000001));

		noOfAccounts++;

		return "{\"id\":\"\", \"name\":\"" + prefix + " " + name + " " + noOfAccounts + "\", \"type\":\"" + type
				+ "\", \"openingBalance\":\"" + openingBalance + "\", \"savingsRate\":\"" + savingsRate
				+ "\", \"enabled\":" + enabled + ", \"addr\":\"" + addr + "\"}";
	}

	@SuppressWarnings("unused")
	private synchronized String generateAccountJsonV2() {
		int prefixIndex = ThreadLocalRandom.current().nextInt(0, prefixes.size());
		String prefix = prefixes.get(prefixIndex);
		int nameIndex = ThreadLocalRandom.current().nextInt(0, names.size());
		String name = names.get(nameIndex);
		int typeIndex = ThreadLocalRandom.current().nextInt(0, types.size());
		String type = types.get(typeIndex);
		String openingBalance = "$" + ThreadLocalRandom.current().nextInt(400, 1001);
		int savingsRateIndex = ThreadLocalRandom.current().nextInt(0, savingsRates.size());
		String savingsRate = savingsRates.get(savingsRateIndex);
		boolean enabled = new Random().nextBoolean();
		String addr1 = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10001));
		String addr2 = String.valueOf(ThreadLocalRandom.current().nextInt(100, 1001));

		noOfAccounts++;

		return "{\"id\":\"\", \"name\":\"" + prefix + " " + name + " " + noOfAccounts + "\", \"type\":\"" + type
				+ "\", \"openingBalance\":\"" + openingBalance + "\", \"savingsRate\":\"" + savingsRate
				+ "\", \"enabled\":" + enabled + ", \"addr1\":\"" + addr1 + "\", \"addr2\":\"" + addr2 + "\"}";
	}

	@SuppressWarnings("unused")
	private synchronized String generateAccountJsonV1() {
		int prefixIndex = ThreadLocalRandom.current().nextInt(0, prefixes.size());
		String prefix = prefixes.get(prefixIndex);
		int nameIndex = ThreadLocalRandom.current().nextInt(0, names.size());
		String name = names.get(nameIndex);
		int typeIndex = ThreadLocalRandom.current().nextInt(0, types.size());
		String type = types.get(typeIndex);
		String openingBalance = "$" + ThreadLocalRandom.current().nextInt(400, 1001);
		int savingsRateIndex = ThreadLocalRandom.current().nextInt(0, savingsRates.size());
		String savingsRate = savingsRates.get(savingsRateIndex);

		noOfAccounts++;

		return "{\"id\":\"\", \"name\":\"" + prefix + " " + name + " " + noOfAccounts + "\", \"type\":\"" + type
				+ "\", \"openingBalance\":\"" + openingBalance + "\", \"savingsRate\":\"" + savingsRate + "\"}";
	}
}
