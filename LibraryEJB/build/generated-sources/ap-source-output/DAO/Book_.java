package DAO;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile CollectionAttribute<Book, Invoice> invoiceCollection;
	public static volatile SingularAttribute<Book, String> name;
	public static volatile SingularAttribute<Book, Boolean> available;
	public static volatile SingularAttribute<Book, Long> numbers;
	public static volatile SingularAttribute<Book, Long> id;

}

