package com.wrkspot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "wrkSpot")
@Data
public class Customer{
	@Id
	@GeneratedValue
	@JdbcTypeCode(SqlTypes.UUID)
	UUID id;
	private String firstName;
	private String lastName;
	private Double spendingLimit;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Address> addresses;
	private String mobileNumber;
	private String customerId;
	private int age;

}