package banking.transactions.domain.repository;

import java.util.Date;
import java.util.List;

import banking.transactions.application.dto.ResponseTransferHistoryDto;
import banking.transactions.domain.entity.Transaction;

public interface TransactionRepository {
	
	List<ResponseTransferHistoryDto> get(String accountNumber, Date fechaInicio, Date fechaFin, int page, int pageSize);
	Transaction save(Transaction transaction);
}
