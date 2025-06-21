package com.wrkSpot.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrkspot.entity.Customer;
import com.wrkspot.model.AddressDto;
import com.wrkspot.model.CustomerDto;
import com.wrkspot.resource.CustomerResource;
import com.wrkspot.service.CustomerService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(CustomerResource.class)
@QuarkusTestResource(InMemoryKafka.class)
public class CustomerControllerTest {

    private static final String TOPIC = "customer-outbound";
    @Inject
    ObjectMapper objectMapper;
    @Inject
    CustomerService customerService;
    @Inject
    @Any
    InMemoryConnector connector;


    @SneakyThrows
    @Test
    public void test() {
        CustomerDto payload = sampleCustomer("John", "Doe", "Dallas", "TX");
        connector.sink(TOPIC).clear();

        given().contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(payload))
                .when()
                .post()                       // POST /customers
                .then()
                .statusCode(201);


        CustomerDto fromDb = customerService.findCustomers("John", null, null, 0, 0).getResults().get(0);
        assertEquals("John", fromDb.getFirstName());

        InMemorySink<Customer> customer = connector.sink(TOPIC);
        Assertions.assertEquals(customer.received().size(), 1);

    }

    private CustomerDto sampleCustomer(String first, String last, String city, String state) {
        AddressDto addr = new AddressDto();
        addr.setAddress1("123 Main St");
        addr.setCity(city);
        addr.setState(state);
        addr.setZipCode("00000");
        addr.setType("home");

        CustomerDto dto = new CustomerDto();
        dto.setFirstName(first);
        dto.setLastName(last);
        dto.setSpendingLimit(1000D);
        dto.setMobileNumber("+1-555-0000");
        dto.setCustomerId("TEST-" + first.toUpperCase());
        dto.setAge(30);
        dto.setAddresses(List.of(addr));
        return dto;
    }
}
