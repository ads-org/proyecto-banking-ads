package banking.customers.domain.entity;

import java.util.Date;
import java.util.Set;

import banking.accounts.domain.entity.BankAccount;

public class Customer {
	private long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String identityDocument;
    private Set<BankAccount> bankAccounts;
    private Date auditCreate;
    private Date auditUpdate;

	public Customer() {
    }

    public String getFullName() {
        return String.format("%s, %s", this.lastName, this.firstName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

	public String getIdentityDocument() {
		return identityDocument;
	}

	public void setIdentityDocument(String identityDocument) {
		this.identityDocument = identityDocument;
	}

	public Date getAuditCreate() {
		return auditCreate;
	}

	public void setAuditCreate(Date auditCreate) {
		this.auditCreate = auditCreate;
	}

	public Date getAuditUpdate() {
		return auditUpdate;
	}

	public void setAuditUpdate(Date auditUpdate) {
		this.auditUpdate = auditUpdate;
	}
    	
}
