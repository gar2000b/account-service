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

import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.bus.EventGenerator;
import com.onlineinteract.workflow.domain.account.repository.AccountRepository;

@Controller
@EnableAutoConfiguration
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	EventGenerator eventGenerator;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = "/account")
	@ResponseBody
	public ResponseEntity<String> createAccount(@RequestBody AccountV1 accountV1) {
		System.out.println("*** createAccount() called ***");
		String accountId = UUID.randomUUID().toString();
		accountV1.setId(accountId);
		accountRepository.createAccount(accountV1);
		eventGenerator.createAccount(accountV1);
		return new ResponseEntity<>("createAccount(): " + accountV1.toString(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = "/account")
	@ResponseBody
	public ResponseEntity<String> updateAccount(@RequestBody AccountV1 accountV1) {
		System.out.println("*** updateAccount() called ***");
		accountRepository.updateAccount(accountV1);
		eventGenerator.updateAccount(accountV1);
		return new ResponseEntity<>("updateAccount(): " + accountV1.toString(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/account/{accountId}")
	@ResponseBody
	public ResponseEntity<String> getAccount(@PathVariable String accountId) {
		System.out.println("*** getAccount() called with accountId of: " + accountId + " ***");
		AccountV1 accountV1 = accountRepository.getAccount(accountId);
		return new ResponseEntity<>("getAccount(): " + accountV1.toString(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/accounts")
	@ResponseBody
	public ResponseEntity<String> getAllAccounts() {
		System.out.println("*** getAllAccounts() called ***");
		String allAccounts = accountRepository.getAllAccounts();
		return new ResponseEntity<>("getAccount(): " + allAccounts, HttpStatus.OK);
	}
}
