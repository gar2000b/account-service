package com.onlineinteract.workflow;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.onlineinteract.workflow.domain.account.bus.DataFixEventGenerator;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository1;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository2;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository3;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.domain.account.v3.AccountV3;
import com.onlineinteract.workflow.domain.account.v3.Clone;

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

	static int noOfAccounts = 0;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountRepository1 accountRepository1;

	@Autowired
	AccountRepository2 accountRepository2;

	@Autowired
	AccountRepository3 accountRepository3;

	@Autowired
	DataFixEventGenerator dataFixEventGenerator;

	public static void main(String[] args) throws InterruptedException {
		AccountDomainTests accountDomainTests = new AccountDomainTests();
		while (true) {
			Thread.sleep(200);
			accountDomainTests.createAccount(generateAccountJsonV3());
		}
	}

	@Test
	public void applyV2ToV3DataFix() {
		List<AccountV2> accountsV2 = accountRepository.getAllAccountsAsList();
		for (AccountV2 accountV2 : accountsV2) {
			if (accountV2.getAddr1() == null || accountV2.getAddr2() == null)
				continue;
			accountV2.setAddr1(accountV2.getAddr1() + " " + accountV2.getAddr2());
			accountV2.setAddr2("");
			try {
				dataFixEventGenerator.updateAccount(accountV2);
				AccountV3 accountV3 = Clone.cloneAccountV3FromV2(accountV2);
				accountRepository.updateAccount(accountV3);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void reconciliationTestV1AgainstV2() {
		List<AccountV1> accountsV1 = accountRepository1.getAllAccountsAsList();
		Map<String, AccountV2> accountsV2 = accountRepository2.getAllAccountsAsMap();

		for (AccountV1 accountV1 : accountsV1) {
			AccountV2 accountV2 = accountsV2.get(accountV1.getId());
			assertEquals(accountV1.getName(), accountV2.getName());
			assertEquals(accountV1.getType(), accountV2.getType());
			assertEquals(accountV1.getOpeningBalance(), accountV2.getOpeningBalance());
			assertEquals(accountV1.getSavingsRate(), accountV2.getSavingsRate());
		}
	}

	@Test
	public void reconciliationTestV2AgainstV3() {
		List<AccountV2> accountsV2 = accountRepository2.getAllAccountsAsList();
		Map<String, AccountV3> accountsV3 = accountRepository3.getAllAccountsAsMap();

		for (AccountV2 accountV2 : accountsV2) {
			AccountV3 accountV3 = accountsV3.get(accountV2.getId());
			assertEquals(accountV2.getName(), accountV3.getName());
			assertEquals(accountV2.getType(), accountV3.getType());
			assertEquals(accountV2.getOpeningBalance(), accountV3.getOpeningBalance());
			assertEquals(accountV2.getSavingsRate(), accountV3.getSavingsRate());
			assertEquals(accountV2.getEnabled(), accountV3.getEnabled());
		}
	}

	private void createAccount(String accountJson) {
		String accountServiceUrl = "http://localhost:9087/account";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(accountJson, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(accountServiceUrl, request, String.class);
		System.out.println("Response from Account Service: " + response.getBody());
	}

	private static String generateAccountJsonV3() {
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
	private static String generateAccountJsonV2() {
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
	private static String generateAccountJsonV1() {
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
