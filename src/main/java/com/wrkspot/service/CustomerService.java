package com.wrkspot.service;

import com.wrkspot.entity.Customer;
import com.wrkspot.kafka.producer.CustomerKafkaProducer;
import com.wrkspot.mapper.CustomerMapper;
import com.wrkspot.model.CustomerDto;
import com.wrkspot.model.PagedResponse;
import com.wrkspot.repository.CustomerRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@JBossLog
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerMapper customerMapper;

    @Inject
    CustomerKafkaProducer customerKafkaProducer;

    @Transactional()
    public CustomerDto create(CustomerDto customerDto) {
        log.infof("CustomerService::create >>> enter");
        log.debugf("with customerDto = %s ", customerDto);
        try {
            Customer customer = customerMapper.toEntity(customerDto);
            customerRepository.persist(customer);
            customerKafkaProducer.send(customer);  // TODO do we need to rollback transaction if kafka failed to send ??
            log.infof("CustomerService::create >>> exit");
            return customerDto;
        } catch (Exception e) {
            log.errorf("CustomerService::create >>> %s", e.getMessage());
            throw e;
        }
    }

    public PagedResponse<CustomerDto> findCustomers(String name, String city, String state, int size, int page) {
        log.infof("CustomerService::findCustomers >>> enter");
        log.debugf("with city = %s and name = %s and state =%s with page =%s and size =%s ", city, name, state, page, size);

        StringBuilder query = new StringBuilder("SELECT DISTINCT c FROM Customer c");
        Parameters parameters = new Parameters();

        boolean joinAddress = StringUtils.isNotBlank(city) || StringUtils.isNotBlank(state);
        if (joinAddress) {
            query.append(" JOIN c.addresses a");
        }

        query.append(" WHERE 1=1");

        if (StringUtils.isNotBlank(name)) {
            query.append(" AND LOWER(c.firstName) LIKE :name");
            parameters.and("name", "%" + name.toLowerCase() + "%");
        }

        if (StringUtils.isNotBlank(city)) {
            query.append(" AND LOWER(a.city) = :city");
            parameters.and("city", city.toLowerCase());
        }

        if (StringUtils.isNotBlank(state)) {
            query.append(" AND LOWER(a.state) = :state");
            parameters.and("state", state.toLowerCase());
        }
        int pageCount = 1;

        PanacheQuery<Customer> customersQuery = customerRepository.findByParameter(query.toString(), parameters);
        if (page > 0 && size > 0) {
            Page pageObject = new Page(page, size);
            customersQuery = customersQuery.page(pageObject);
            pageCount = customersQuery.pageCount();
        }
        long totalRecords = customersQuery.count();


        List<CustomerDto> customers = customersQuery.list().stream().map(customerMapper::map).toList();
        log.infof("CustomerService::findCustomers >>> exit with customer found %d", customers.size());
        return new PagedResponse<>(customers, totalRecords, pageCount);
    }
}
