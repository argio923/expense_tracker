package com.argio.resource;

import com.argio.dto.expense.ExpenseResponseDto;
import com.argio.dto.expense.RegisterExpenseDto;
import com.argio.enums.ExpenseCategory;
import com.argio.service.ExpenseService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import java.time.LocalDate;
import java.util.UUID;

@Path( "/expenses")
@Authenticated
@Consumes("application/json")
@Produces("application/json")
public class ExpenseResource {

    @Inject ExpenseService service;

    @GET
    @Path("/get")

    public Response getExpense(
        @QueryParam("id") UUID id
    ) {
        var expense = service.get(id);
        return Response.ok(expense).build();
    }

    @GET
    @Path("/get-list")
    public Response getExpenseList(
        @QueryParam("dateFrom") LocalDate dateFrom,
        @QueryParam("dateTo") LocalDate dateTo,
        @QueryParam("category") ExpenseCategory category
    ) {
        var expenseList = service.getListByUser(dateFrom, dateTo, category);
        return Response.ok(expenseList).build();
    }

    @POST
    @Path("/new-expense")
    public Response createExpense(@Valid RegisterExpenseDto expenseRequest) {
        service.registerExpense(expenseRequest);
        return Response.ok().build();
    }
}