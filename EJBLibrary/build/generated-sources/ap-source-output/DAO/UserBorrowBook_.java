package DAO;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserBorrowBook.class)
public abstract class UserBorrowBook_ {

	public static volatile SingularAttribute<UserBorrowBook, Long> numbers;
	public static volatile SingularAttribute<UserBorrowBook, Date> end;
	public static volatile SingularAttribute<UserBorrowBook, Long> id;
	public static volatile SingularAttribute<UserBorrowBook, String> userName;
	public static volatile SingularAttribute<UserBorrowBook, String> bookName;
	public static volatile SingularAttribute<UserBorrowBook, Date> begin;
	public static volatile SingularAttribute<UserBorrowBook, User> userId;
	public static volatile SingularAttribute<UserBorrowBook, Boolean> status;
	public static volatile SingularAttribute<UserBorrowBook, Book> bookId;

}

