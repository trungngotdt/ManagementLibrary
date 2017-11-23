package DAO;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserRole.class)
public abstract class UserRole_ {

	public static volatile SingularAttribute<UserRole, Role> role;
	public static volatile SingularAttribute<UserRole, UserRolePK> userRolePK;
	public static volatile SingularAttribute<UserRole, String> userName;
	public static volatile SingularAttribute<UserRole, User> user;

}

