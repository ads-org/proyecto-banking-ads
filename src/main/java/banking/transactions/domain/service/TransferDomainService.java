package banking.transactions.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import banking.accounts.domain.entity.BankAccount;
import banking.common.application.Notification;
import banking.transactions.domain.entity.Transaction;

@Service
public class TransferDomainService {
	
	@Autowired
	MessageSource messageSource;
	
	public void performTransfer(Transaction transaction)
			throws IllegalArgumentException {
		Notification notification = this.validation(transaction.getAccountOrigin(), transaction.getAccountDestination(), transaction.getMount());
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        transaction.getAccountOrigin().withdrawMoney(transaction.getMount());
        transaction.getAccountDestination().depositMoney(transaction.getMount());
	}
	
	public void performDeposit(Transaction transaction)
			throws IllegalArgumentException {
		Notification notification = this.validation(transaction.getAccountDestination(), transaction.getMount());
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }        
        transaction.getAccountDestination().depositMoney(transaction.getMount());
	}
	
	public void performWithdraw(Transaction transaction)
			throws IllegalArgumentException {
		Notification notification = this.validation(transaction.getAccountOrigin(), transaction.getMount());
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }        
        transaction.getAccountOrigin().withdrawMoney(transaction.getMount());
	}
	
	private Notification validation(BankAccount originAccount, BankAccount destinationAccount, BigDecimal amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccounts(notification, originAccount, destinationAccount);
        return notification;
    }
	
	private Notification validation(BankAccount account, BigDecimal amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccounts(notification, account);
        return notification;
    }
    
    private void validateAmount(Notification notification, BigDecimal amount) {
        if (amount == null) {
            notification.addError(messageSource.getMessage("amount.missing", null, LocaleContextHolder.getLocale()));
            return;
        }
        if (amount.signum() <= 0) {
            notification.addError(messageSource.getMessage("amount.must.greater.zero", null, LocaleContextHolder.getLocale()));
        }
    }
    
    private void validateBankAccounts(Notification notification, BankAccount originAccount, BankAccount destinationAccount) {
        if (originAccount == null || destinationAccount == null) {
            notification.addError(messageSource.getMessage("cannot.perform.transfer.invalid.data", null, LocaleContextHolder.getLocale()));
            return;
        }
        if (originAccount.getNumber().equals(destinationAccount.getNumber())) {
            notification.addError(messageSource.getMessage("cannot.transfer.money.same.account", null, LocaleContextHolder.getLocale()));
        }
    }
    
    private void validateBankAccounts(Notification notification, BankAccount account) {
    	if (account == null) {
            notification.addError(messageSource.getMessage("cannot.perform.transfer.invalid.data", null, LocaleContextHolder.getLocale()));
            return;
        }      
    }
}
