package com.onlineinteract.workflow.domain.account.bus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onlineinteract.workflow.domain.account.AccountEvent;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;

@Component
public class EventGenerator {

	@Autowired
	private Producer producer;

	public void createAccount(AccountV1 accountV1) {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountCreatedEvent");
		accountEvent.setV1(accountV1);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV1().getId().toString());
		System.out.println("AccountCreatedEvent Published to account-event-topic");
	}

	public void updateAccount(AccountV1 accountV1) {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountUpdatedEvent");
		accountEvent.setV1(accountV1);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV1().getId().toString());
		System.out.println("AccountUpdatedEvent Published to account-event-topic");
	}
}
