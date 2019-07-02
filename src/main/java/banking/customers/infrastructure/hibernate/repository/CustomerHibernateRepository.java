package banking.customers.infrastructure.hibernate.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;

@Transactional
@Repository
public class CustomerHibernateRepository extends BaseHibernateRepository<Customer> implements CustomerRepository {
	public Customer get(long customerId) {
		Customer customer = null;
		customer = getSession().get(Customer.class, customerId);
		return customer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> get(int page, int pageSize) {
		
		List<Customer> customers = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT  ");
		sql.append("	c.customer_id AS id, ");
		sql.append("	c.first_name AS firstName, " );
		sql.append("	c.last_name AS lastName, ");
		sql.append("	c.identity_document AS identityDocument, ");
		sql.append("	c.active as isActive ");
		sql.append("FROM customer c ");
		sql.append("JOIN (SELECT c2.customer_id FROM customer c2 ORDER BY c2.last_name ASC, c2.first_name ASC LIMIT :page, :pageSize) ");
		sql.append("AS c3 ON c.customer_id = c3.customer_id ");		
		sql.append("ORDER BY c.last_name ASC, c.first_name ASC; ");		
		
		SQLQuery query = getSession().createNativeQuery(sql.toString());
		
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("firstName", StandardBasicTypes.STRING);
		query.addScalar("lastName", StandardBasicTypes.STRING);
		query.addScalar("identityDocument", StandardBasicTypes.STRING);
		query.addScalar("isActive", StandardBasicTypes.BOOLEAN);
								
		query.setParameter("page", page);
		query.setParameter("pageSize", pageSize);
		query.setResultTransformer(Transformers.aliasToBean(Customer.class));
		customers = query.getResultList();
		
		return customers;
		
	}
	
	public Customer save(Customer customer) {
		return super.save(customer);
	}
}
