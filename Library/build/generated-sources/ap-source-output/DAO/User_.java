package DAO;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile CollectionAttribute<User, UserRole> userRoleCollection;
	public static volatile CollectionAttribute<User, UserBorrowBook> userBorrowBookCollection;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Long> id;

}

