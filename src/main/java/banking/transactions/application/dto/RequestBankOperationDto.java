package banking.transactions.application.dto;

import java.math.BigDecimal;

public class RequestBankOperationDto {
	private String accountNumber;	
	private BigDecimal amount;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
		
}
