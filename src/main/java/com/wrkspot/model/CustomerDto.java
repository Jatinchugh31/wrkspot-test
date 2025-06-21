package com.wrkspot.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotNull
	private Double spendingLimit;
	@NotEmpty
	private List<@Valid AddressDto> addresses;
	@NotBlank
	private String mobileNumber;
	@NotBlank
	private String customerId;
	private int age;

}