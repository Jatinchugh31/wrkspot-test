package com.wrkspot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotBlank
	private String zipCode;

	private String address2;
	@NotBlank
	private String city;
	@NotBlank
	private String address1;
	@NotBlank
	private String state;
	@NotBlank
	private String type;
}
