package com.argio.resource;

import com.argio.dto.expense.RegisterExpenseDto;
import com.argio.dto.expense.UpdateExpenseDto;
import com.argio.enums.ExpenseCategory;
import com.argio.service.ExpenseService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.UUID;

/**
 * RESTful resource for managing expenses.
 * Provides endpoints to retrieve, create, and update expense entries for the authenticated user.
 * Supports filtering and categorization of expense data.
 */
@Path( "/expenses")
@Authenticated
@Consumes("application/json")
@Produces("application/json")
public class ExpenseResource {

    @Inject ExpenseService service;

    /**
     * Retrieves an expense record by its unique identifier.
     * This method is part of the "/expenses" RESTful API resource
     * and requires the client to provide the UUID of the specific expense.
     *
     * @param id the unique identifier of the expense to be retrieved. Must be a valid UUID.
     * @return a {@link Response} object representing the HTTP response.
     *         If the expense exists, the response will contain the expense details
     *         wrapped in a {@link com.argio.dto.ExpenseResponseDto} and have a status of 200 (OK).
     *         If the expense is not found, a 404 (Not Found) response will be returned.
     */
    @GET
    @Path("/{id}")
    public Response getExpense(
        @PathParam("id") UUID id
    ) {
        var expense = service.get(id);
        return Response.ok(expense).build();
    }

    /**
     * Retrieves a list of expenses for the currently authenticated user, filtered by the provided date range and category.
     * The endpoint allows clients to query expenses based on specific criteria such as
     * start date, end date, and category. If any of the query parameters are omitted,
     * default behavior will be applied (e.g., no filtering by the missing criteria).
     *
     * @param dateFrom the starting date of the expense filter range (inclusive). Can be null to ignore this filter.
     * @param dateTo the ending date of the expense filter range (inclusive). Can be null to ignore this filter.
     * @param category the category to filter the expenses by. Can be null to include all categories.
     * @return a {@link Response} object containing the filtered list of expenses.
     *         The response body will be a JSON array of {@link ExpenseResponseDto} objects, and the response status will be 200 (OK).
     */
    @GET
    public Response getExpenseList(
        @QueryParam("dateFrom" ) LocalDate dateFrom,
        @QueryParam("dateTo") LocalDate dateTo,
        @QueryParam("category") ExpenseCategory category
    ) {
        var expenseList = service.getListByUser(dateFrom, dateTo, category);
        return Response.ok(expenseList).build();
    }

    /**
     * Creates a new expense record for the currently authenticated user.
     * This method accepts a data transfer object ({@code RegisterExpenseDto})
     * containing the details of the expense and persists it in the system.
     *
     * @param expenseRequest the data transfer object containing the details of the expense to be created,
     *                       including amount, category, description, and expense date; must not be null and
     *                       must meet all validation constraints.
     * @return a {@link Response} object representing the HTTP response.
     *         Returns a 200 (OK) status if the expense is successfully created.
     */
    @POST
    public Response createExpense(@Valid RegisterExpenseDto expenseRequest) {
        service.registerExpense(expenseRequest);
        return Response.ok().build();
    }

    /**
     * Updates the details of an existing expense record.
     * This method accepts a data transfer object containing the updated expense
     * information and updates the corresponding record in the system.
     *
     * @param expenseRequest the data transfer object containing the updated details
     *                       of the expense to be applied. It includes the expense
     *                       identifier, amount, category, description, and expense
     *                       date. The parameter must be valid and non-null.
     * @return a {@link Response} object indicating the result of the operation.
     *         Returns a 200 (OK) status if the update is successful.
     */
    @PUT
    public Response updateExpense(@Valid UpdateExpenseDto expenseRequest) {
        service.updateExpense(expenseRequest);
        return Response.ok().build();
    }
}