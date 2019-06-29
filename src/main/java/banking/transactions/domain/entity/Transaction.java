package banking.transactions.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import banking.accounts.domain.entity.BankAccount;
import banking.users.domain.entity.User;

public class Transaction {
	
	private long id;
	private BankAccount accountOrigin;
	private BankAccount accountDestination;
	private BigDecimal mount;
	private User user;	
	private Date dateTransfer;
	
	
	public Transaction() {
		super();
	}

	public Transaction(BankAccount accountFrom, BankAccount accountto, BigDecimal mount) {
		super();
		this.accountOrigin = accountFrom;
		this.accountDestination = accountto;
		this.mount = mount;
		this.dateTransfer = new Date();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BankAccount getAccountOrigin() {
		return accountOrigin;
	}
	public void setAccountOrigin(BankAccount accountFrom) {
		this.accountOrigin = accountFrom;
	}
	public BankAccount getAccountDestination() {
		return accountDestination;
	}
	public void setAccountDestination(BankAccount accountto) {
		this.accountDestination = accountto;
	}
	public BigDecimal getMount() {
		return mount;
	}
	public void setMount(BigDecimal mount) {
		this.mount = mount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDateTransfer() {
		return dateTransfer;
	}
	public void setDateTransfer(Date dateTransfer) {
		this.dateTransfer = dateTransfer;
	} 
	
}
