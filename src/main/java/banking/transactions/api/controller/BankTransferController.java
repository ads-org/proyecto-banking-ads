package banking.transactions.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import banking.common.api.controller.ResponseHandler;
import banking.transactions.application.TransactionApplicationService;
import banking.transactions.application.dto.RequestBankTransferDto;
import banking.transactions.application.dto.ResponseTransferHistoryDto;

@RestController
@RequestMapping("api/transactions")
public class BankTransferController {
	@Autowired
	TransactionApplicationService transactionApplicationService;
	
	@Autowired
	ResponseHandler responseHandler;

	@RequestMapping(method = RequestMethod.POST, path = "/transfer", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> performTransfer(@RequestBody RequestBankTransferDto requestBankTransferDto) throws Exception {
		try {
			transactionApplicationService.performTransfer(requestBankTransferDto);
			return this.responseHandler.getResponse("Transfer done!", HttpStatus.CREATED);
		} catch(IllegalArgumentException ex) {
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			return this.responseHandler.getAppExceptionResponse();
		}
	}
	
		
	@RequestMapping(method = RequestMethod.GET, path = "/{accountNumber}/history", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> get(
			@PathVariable("accountNumber") String accountNumber,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
    		@RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) throws Exception {
		try {
			List<ResponseTransferHistoryDto> transactions = transactionApplicationService.get(accountNumber, page, pageSize);
			return this.responseHandler.getDataResponse(transactions, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			return this.responseHandler.getAppExceptionResponse();
		}
	}
	
}
