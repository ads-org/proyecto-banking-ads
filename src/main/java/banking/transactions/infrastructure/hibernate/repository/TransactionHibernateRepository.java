package banking.transactions.infrastructure.hibernate.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.transactions.application.dto.ResponseTransferHistoryDto;
import banking.transactions.domain.entity.Transaction;
import banking.transactions.domain.repository.TransactionRepository;

@Transactional
@Repository
public class TransactionHibernateRepository extends BaseHibernateRepository<Transaction> 
		implements TransactionRepository {

	@Override
	public Transaction save(Transaction transaction) {
		return super.save(transaction);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResponseTransferHistoryDto> get(String accountNumber, Date startDate, Date endDate, int page, int pageSize) {
		
		List<ResponseTransferHistoryDto> transactions = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT  ");
		sql.append("	a.transaction_id as id,");
		sql.append("	b.`number` as accountOrigin, " );
		sql.append("	c.`number` as accountDestination, ");
		sql.append("	a.mount as mount, ");
		sql.append("	CONVERT_TZ(a.date_transfer,'+00:00','-05:00') as dateTransfer ");
		sql.append("FROM `transaction` a ");
		sql.append("LEFT JOIN bank_account b ON a.account_origin_id = b.bank_account_id ");
		sql.append("LEFT JOIN bank_account c ON a.account_destination_id = c.bank_account_id ");
		sql.append("WHERE (b.`number` = :accountNumber OR c.`number` = :accountNumber) ");
						
		if (startDate != null) {
			sql.append("AND a.date_transfer >= :fechaInicio ");			
		}
		
		if (endDate != null) {
			sql.append("AND a.date_transfer < :fechaFin  ");			
		}
		
		sql.append("ORDER BY a.date_transfer DESC ");
		sql.append("LIMIT :page, :pageSize");
		
		SQLQuery query = getSession().createNativeQuery(sql.toString());
		
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("accountOrigin", StandardBasicTypes.STRING);
		query.addScalar("accountDestination", StandardBasicTypes.STRING);
		query.addScalar("mount", StandardBasicTypes.BIG_DECIMAL);
		query.addScalar("dateTransfer", StandardBasicTypes.TIMESTAMP);
		
		if (startDate != null) {
			query.setParameter("fechaInicio", startDate, TemporalType.DATE);
		}
		
		if (endDate != null) {
			query.setParameter("fechaFin", endDate, TemporalType.DATE);
		}
				
		query.setParameter("accountNumber", accountNumber);
		query.setParameter("page", page);
		query.setParameter("pageSize", pageSize);
		query.setResultTransformer(Transformers.aliasToBean(ResponseTransferHistoryDto.class));
		transactions = query.getResultList();			
		
		return transactions;
	}

}
