package com.wrkspot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(schema = "wrkSpot")
@Data
public class Address {
	@Id
	@GeneratedValue
	@JdbcTypeCode(SqlTypes.UUID)
	private UUID  id;
	private String zipCode;
	private String address2;
	private String city;
	private String address1;
	private String state;
	private String type;

	@ManyToOne
	@JoinColumn(name="customer_id",referencedColumnName = "id")
	@ToString.Exclude
	@JsonBackReference
	private Customer customer;
}
