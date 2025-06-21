// src/main/java/com/wrkspot/resource/CustomerResource.java
package com.wrkspot.resource;

import com.wrkspot.model.CustomerDto;
import com.wrkspot.model.PagedResponse;
import com.wrkspot.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JBossLog
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    public Response create(CustomerDto dto) {
        CustomerDto saved = customerService.create(dto);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    public Response getCustomers(
            @QueryParam("name") String name,
            @QueryParam("city") String city,
            @QueryParam("state") String state,
            @QueryParam("page") int page,
            @QueryParam("size") int size
    ) {

        PagedResponse<CustomerDto> customerDtos = customerService.findCustomers(name, city, state, page, size);
        return Response.ok(customerDtos).build();
    }

}
