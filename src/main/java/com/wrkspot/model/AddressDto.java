package com.wrkspot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

	private String zipCode;
	private String address2;
	private String city;
	private String address1;
	private String state;
	private String type;
}
