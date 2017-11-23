package DAO;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserBorrowBook.class)
public abstract class UserBorrowBook_ {

	public static volatile SingularAttribute<UserBorrowBook, UserBorrowBookPK> userBorrowBookPK;
	public static volatile SingularAttribute<UserBorrowBook, Book> book;
	public static volatile SingularAttribute<UserBorrowBook, Long> bookNumbers;
	public static volatile SingularAttribute<UserBorrowBook, String> userName;
	public static volatile SingularAttribute<UserBorrowBook, String> bookName;
	public static volatile SingularAttribute<UserBorrowBook, User> user;

}

