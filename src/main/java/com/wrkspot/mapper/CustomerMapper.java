package com.wrkspot.mapper;

import com.wrkspot.entity.Customer;
import com.wrkspot.model.CustomerDto;
import org.mapstruct.*;

@Mapper(componentModel = "cdi" , uses = {AddressMapper.class})
public interface CustomerMapper {

    CustomerDto map(Customer customer);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDto dto);

    @AfterMapping
    default void setCustomerInAddresses(@MappingTarget Customer customer) {
        if (customer.getAddresses() != null) {
            customer.getAddresses().forEach(addr -> addr.setCustomer(customer));
        }
    }

}
