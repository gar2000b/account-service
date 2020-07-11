package com.onlineinteract.workflow.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineinteract.workflow.domain.account.Account;
import com.onlineinteract.workflow.repository.AccountRepository;
import com.onlineinteract.workflow.utility.JsonParser;

@Controller
@EnableAutoConfiguration
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = "/account")
	@ResponseBody
	public ResponseEntity<String> createAccount(@RequestBody Account account) {
		System.out.println("*** createAccount() called ***");
		String accountId = UUID.randomUUID().toString();
		account.setId(accountId);
		accountRepository.createAccount(account);
		return new ResponseEntity<>("createAccount(): " + JsonParser.toJson(account), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/account/{accountId}")
	@ResponseBody
	public ResponseEntity<String> getAccount(@PathVariable String accountId) {
		System.out.println("*** getAccount() called with accountId of: " + accountId + " ***");
		Account account = accountRepository.getAccount(accountId);
		return new ResponseEntity<>("getAccount(): " + JsonParser.toJson(account), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/accounts")
	@ResponseBody
	public ResponseEntity<String> getAllAccounts() {
		System.out.println("*** getAllAccounts() called ***");
		String allAccounts = accountRepository.getAllAccounts();
		return new ResponseEntity<>("getAccount(): " + allAccounts, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = "/account")
	@ResponseBody
	public ResponseEntity<String> updateAccount(@RequestBody Account account) {
		System.out.println("*** updateAccount() called ***");
		accountRepository.updateAccount(account);
		return new ResponseEntity<>("updateAccount(): " + JsonParser.toJson(account), HttpStatus.OK);
	}
}
