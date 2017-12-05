package DAO;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Invoice.class)
public abstract class Invoice_ {

	public static volatile SingularAttribute<Invoice, Student> student;
	public static volatile SingularAttribute<Invoice, Book> book;
	public static volatile SingularAttribute<Invoice, Long> numbers;
	public static volatile SingularAttribute<Invoice, InvoicePK> invoicePK;
	public static volatile SingularAttribute<Invoice, Date> end;
	public static volatile SingularAttribute<Invoice, Employee> employee;
	public static volatile SingularAttribute<Invoice, Date> begin;

}

