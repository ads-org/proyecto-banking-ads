package banking.transactions.application;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import banking.accounts.domain.entity.BankAccount;
import banking.accounts.domain.repository.BankAccountRepository;
import banking.common.application.Notification;
import banking.common.application.enumeration.RequestBodyType;
import banking.transactions.application.dto.RequestBankOperationDto;
import banking.transactions.application.dto.RequestBankTransferDto;
import banking.transactions.application.dto.ResponseTransferHistoryDto;
import banking.transactions.domain.entity.Transaction;
import banking.transactions.domain.repository.TransactionRepository;
import banking.transactions.domain.service.TransferDomainService;

@Service
public class TransactionApplicationService {
	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private TransferDomainService transferDomainService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Value("${maxPageSize}")
	private int maxPageSize;
	
	@Autowired
	MessageSource messageSource;
	
	@Transactional
	public void performTransfer(RequestBankTransferDto requestBankTransferDto) throws Exception {
		Notification notification = this.validation(requestBankTransferDto);				
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        
		BankAccount originAccount = this.bankAccountRepository.findByNumberLocked(requestBankTransferDto.getFromAccountNumber());
		BankAccount destinationAccount = this.bankAccountRepository.findByNumberLocked(requestBankTransferDto.getToAccountNumber());
		Transaction transaction = new Transaction(originAccount, destinationAccount, requestBankTransferDto.getAmount());
		this.transferDomainService.performTransfer(transaction);
		this.bankAccountRepository.save(originAccount);
		this.bankAccountRepository.save(destinationAccount);				
		this.transactionRepository.save(transaction);
	}
	
	@Transactional
	public void performDeposit(RequestBankOperationDto requestBankOperationDto) throws Exception {
		Notification notification = this.validation(requestBankOperationDto);				
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        
		BankAccount destinationAccount = this.bankAccountRepository.findByNumberLocked(requestBankOperationDto.getAccountNumber());		
		Transaction transaction = new Transaction(null, destinationAccount, requestBankOperationDto.getAmount());
		this.transferDomainService.performDeposit(transaction);		
		this.bankAccountRepository.save(destinationAccount);				
		this.transactionRepository.save(transaction);
	}
	
	@Transactional
	public void performWithdraw(RequestBankOperationDto requestBankOperationDto) throws Exception {
		Notification notification = this.validation(requestBankOperationDto);				
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        
		BankAccount originAccount = this.bankAccountRepository.findByNumberLocked(requestBankOperationDto.getAccountNumber());		
		Transaction transaction = new Transaction(originAccount, null, requestBankOperationDto.getAmount());
		this.transferDomainService.performWithdraw(transaction);		
		this.bankAccountRepository.save(originAccount);				
		this.transactionRepository.save(transaction);
	}

	
	private Notification validation(RequestBankTransferDto requestBankTransferDto) {
		Notification notification = new Notification();
		if (requestBankTransferDto == null || requestBankTransferDto.getFromAccountNumber().equals(RequestBodyType.INVALID.toString())) {
			notification.addError(messageSource.getMessage("invalid.json.data", null, LocaleContextHolder.getLocale()));
		}
		return notification;
	}
	
	private Notification validation(RequestBankOperationDto requestBankOperationDto) {
		Notification notification = new Notification();
		if (requestBankOperationDto == null || requestBankOperationDto.getAccountNumber().equals(RequestBodyType.INVALID.toString())) {
			notification.addError(messageSource.getMessage("invalid.json.data", null, LocaleContextHolder.getLocale()));
		}
		return notification;
	}
	
	public List<ResponseTransferHistoryDto> get(String accountNumber, Date startDate, Date endDate, int page, int pageSize){
		Notification notification = this.getPaginatedValidation(page, pageSize);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
		List<ResponseTransferHistoryDto> transactions = transactionRepository.get(accountNumber, startDate, endDate, page, pageSize);
		return transactions;
	}
	
	private Notification getPaginatedValidation(int page, int pageSize) {
		Notification notification = new Notification();
		if (pageSize > maxPageSize) {
			notification.addError(messageSource.getMessage("page.size.cannot.greate", null, LocaleContextHolder.getLocale()));
		}
		return notification;
	}
}
