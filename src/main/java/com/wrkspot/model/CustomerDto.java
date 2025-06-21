package com.wrkspot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

	private String firstName;
	private String lastName;
	private Double spendingLimit;
	private List<AddressDto> addresses;
	private String mobileNumber;
	private String customerId;
	private int age;

}