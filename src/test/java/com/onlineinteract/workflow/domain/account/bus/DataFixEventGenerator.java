package com.onlineinteract.workflow.domain.account.bus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineinteract.workflow.domain.account.AccountEvent;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.domain.account.v2.Clone;

public class DataFixEventGenerator {

	@Autowired
	private Producer producer;

	public void updateAccount(AccountV2 accountV2) {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountUpdatedEvent");
		accountEvent.setVersion(2L);
		accountEvent.setV2(accountV2);

		AccountV1 accountV1 = Clone.cloneAccountV1FromV2(accountV2);
		accountEvent.setV1(accountV1);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV2().getId().toString());
		System.out.println("AccountUpdatedEvent Published to account-event-topic");
	}
}
