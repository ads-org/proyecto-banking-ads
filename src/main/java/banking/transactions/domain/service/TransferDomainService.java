package banking.transactions.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import banking.accounts.domain.entity.BankAccount;
import banking.common.application.Notification;
import banking.transactions.domain.entity.Transaction;

@Service
public class TransferDomainService {
	public void performTransfer(Transaction transaction)
			throws IllegalArgumentException {
		Notification notification = this.validation(transaction.getAccountOrigin(), transaction.getAccountDestination(), transaction.getMount());
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        transaction.getAccountOrigin().withdrawMoney(transaction.getMount());
        transaction.getAccountDestination().depositMoney(transaction.getMount());
	}
	
	private Notification validation(BankAccount originAccount, BankAccount destinationAccount, BigDecimal amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccounts(notification, originAccount, destinationAccount);
        return notification;
    }
    
    private void validateAmount(Notification notification, BigDecimal amount) {
        if (amount == null) {
            notification.addError("amount is missing");
            return;
        }
        if (amount.signum() <= 0) {
            notification.addError("The amount must be greater than zero");
        }
    }
    
    private void validateBankAccounts(Notification notification, BankAccount originAccount, BankAccount destinationAccount) {
        if (originAccount == null || destinationAccount == null) {
            notification.addError("Cannot perform the transfer. Invalid data in bank accounts specifications");
            return;
        }
        if (originAccount.getNumber().equals(destinationAccount.getNumber())) {
            notification.addError("Cannot transfer money to the same bank account");
        }
    }
}
