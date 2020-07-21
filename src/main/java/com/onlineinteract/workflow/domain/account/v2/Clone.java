package com.onlineinteract.workflow.domain.account.v2;

import com.onlineinteract.workflow.domain.account.v1.AccountV1;

public class Clone {
	public static AccountV1 cloneAccountV1FromV2(AccountV2 accountV2) {
		AccountV1 accountV1 = new AccountV1();
		accountV1.setId(accountV2.getId());
		accountV1.setName(accountV2.getName());
		accountV1.setOpeningBalance(accountV2.getOpeningBalance());
		accountV1.setSavingsRate(accountV2.getSavingsRate());
		accountV1.setType(accountV2.getType());
		return accountV1;
	}
}
