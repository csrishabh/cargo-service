package com.cargo.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Consignment.class)
public class Consignment_ {
	 public static volatile SingularAttribute<Consignment, Date> entry_Date;
	 public static volatile SingularAttribute<Consignment, String> Status;
	 public static volatile SingularAttribute<Consignment, String> payment_Type;
	 public static volatile SingularAttribute<Consignment, Boolean> isDeleted;
}
