package com.wrkspot.repository;

import com.wrkspot.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository  implements PanacheRepositoryBase<Customer, UUID> {


    public PanacheQuery<Customer> findByParameter(String query, Parameters parameters) {
        return  find(query, parameters);
    }

   public List<Customer> findByParameter(String query, Parameters parameters, Page page, Sort sort) {
        return  find(query, parameters,sort).page(page).list();
    }


    public boolean customerPresent(String customerId) {
     return  find("customerId", customerId).list().stream().findAny().isPresent();
    }
}
