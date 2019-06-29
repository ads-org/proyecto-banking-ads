package banking.transactions.infrastructure.hibernate.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
	public List<ResponseTransferHistoryDto> get(String accountNumber, int page, int pageSize) {
						
		List<ResponseTransferHistoryDto> transactions = null;
		Criteria criteria = getSession().createCriteria(Transaction.class, "a");
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("a.id"),"id");
		projList.add(Projections.property("b.number"),"accountOrigin");
		projList.add(Projections.property("c.number"),"accountDestination");
		projList.add(Projections.property("a.mount"), "mount");
		projList.add(Projections.property("a.dateTransfer"), "dateTransfer");
		criteria.setProjection(projList);
						
		criteria.createAlias("a.accountOrigin", "b");		
		criteria.createAlias("a.accountDestination", "c");		
		criteria.add(Restrictions.or(Restrictions.eq("b.number", accountNumber), 
				Restrictions.eq("c.number",accountNumber)));		
		criteria.addOrder(Order.asc("a.dateTransfer"));
		criteria.setFirstResult(page);
		criteria.setMaxResults(pageSize);
		criteria.setResultTransformer(Transformers.aliasToBean(ResponseTransferHistoryDto.class));
		transactions = criteria.list();
		
		return transactions;
	}

}
