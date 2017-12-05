package DAO;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile CollectionAttribute<Student, Invoice> invoiceCollection;
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, Long> id;

}

