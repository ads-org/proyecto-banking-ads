package banking.transactions.application.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseTransferHistoryDto {
	
	private long id;
	private String accountOrigin;
	private String accountDestination;
	private BigDecimal mount;
		
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date dateTransfer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountOrigin() {
		return accountOrigin;
	}

	public void setAccountOrigin(String accountOrigin) {
		this.accountOrigin = accountOrigin;
	}

	public String getAccountDestination() {
		return accountDestination;
	}

	public void setAccountDestination(String accountDestination) {
		this.accountDestination = accountDestination;
	}

	public BigDecimal getMount() {
		return mount;
	}

	public void setMount(BigDecimal mount) {
		this.mount = mount;
	}

	public Date getDateTransfer() {
		return dateTransfer;
	}

	public void setDateTransfer(Date dateTransfer) {
		this.dateTransfer = dateTransfer;
	}

	
}
