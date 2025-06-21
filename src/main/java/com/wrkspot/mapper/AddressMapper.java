package com.wrkspot.mapper;

import com.wrkspot.entity.Address;
import com.wrkspot.model.AddressDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AddressMapper {

    AddressDto toDto(Address entity);

    @InheritInverseConfiguration
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "customer", ignore = true)
    Address toEntity(AddressDto dto);
}
