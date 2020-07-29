package com.onlineinteract.workflow.domain.account.v3;

import com.onlineinteract.workflow.domain.account.v1.AccountV1;
import com.onlineinteract.workflow.domain.account.v2.AccountV2;
import com.onlineinteract.workflow.domain.account.v3.AccountV3;

public class Clone {

	public static AccountV2 cloneAccountV2FromV3(AccountV3 accountV3) {
		AccountV2 accountV2 = new AccountV2();
		accountV2.setId(accountV3.getId());
		accountV2.setName(accountV3.getName());
		accountV2.setOpeningBalance(accountV3.getOpeningBalance());
		accountV2.setSavingsRate(accountV3.getSavingsRate());
		accountV2.setType(accountV3.getType());
		accountV2.setEnabled(accountV3.getEnabled());
		accountV2.setAddr1(accountV3.getAddr());
		accountV2.setAddr2("");
		return accountV2;
	}
	
	public static AccountV3 cloneAccountV3FromV2(AccountV2 accountV2) {
		AccountV3 accountV3 = new AccountV3();
		accountV3.setId(accountV2.getId());
		accountV3.setName(accountV2.getName());
		accountV3.setOpeningBalance(accountV2.getOpeningBalance());
		accountV3.setSavingsRate(accountV2.getSavingsRate());
		accountV3.setType(accountV2.getType());
		accountV3.setEnabled(accountV2.getEnabled());
		accountV3.setAddr(accountV2.getAddr1());
		return accountV3;
	}
	
	public static AccountV1 cloneAccountV1FromV3(AccountV3 accountV3) {
		AccountV1 accountV1 = new AccountV1();
		accountV1.setId(accountV3.getId());
		accountV1.setName(accountV3.getName());
		accountV1.setOpeningBalance(accountV3.getOpeningBalance());
		accountV1.setSavingsRate(accountV3.getSavingsRate());
		accountV1.setType(accountV3.getType());
		return accountV1;
	}
}