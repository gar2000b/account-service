package com.onlineinteract.workflow.domain.account.bus;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onlineinteract.workflow.domain.account.AccountEvent;
import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.domain.account.v3.AccountV3;
import com.onlineinteract.workflow.domain.account.v3.Clone;

@Component
public class EventGenerator {

	@Autowired
	private Producer producer;

	public void createAccount(AccountV3 accountV3) throws InterruptedException, ExecutionException {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountCreatedEvent");
		accountEvent.setVersion(3L);
		accountEvent.setV3(accountV3);

		AccountV2 accountV2 = Clone.cloneAccountV2FromV3(accountV3);
		accountEvent.setV2(accountV2);
		AccountV1 accountV1 = Clone.cloneAccountV1FromV3(accountV3);
		accountEvent.setV1(accountV1);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV3().getId().toString());
//		System.out.println("AccountCreatedEvent Published to account-event-topic");
	}

	public void updateAccount(AccountV3 accountV3) throws InterruptedException, ExecutionException {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setCreated(new Date().getTime());
		accountEvent.setEventId(String.valueOf(accountEvent.getCreated()));
		accountEvent.setEventType("AccountUpdatedEvent");
		accountEvent.setVersion(3L);
		accountEvent.setV3(accountV3);

		AccountV2 accountV2 = Clone.cloneAccountV2FromV3(accountV3);
		accountEvent.setV2(accountV2);
		AccountV1 accountV1 = Clone.cloneAccountV1FromV3(accountV3);
		accountEvent.setV1(accountV1);

		producer.publishRecord("account-event-topic", accountEvent, accountEvent.getV3().getId().toString());
//		System.out.println("AccountUpdatedEvent Published to account-event-topic");
	}
}
