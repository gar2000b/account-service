package com.onlineinteract.workflow.events;

import com.onlineinteract.workflow.domain.account.Account;

public class AccountUpdatedEvent extends Event {
	private Account account;
	
	public AccountUpdatedEvent() {
	}
	
	public AccountUpdatedEvent(Account account) {
		this.account = account;
		this.setType("AccountUpdatedEvent");
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountUpdatedEvent other = (AccountUpdatedEvent) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
}
