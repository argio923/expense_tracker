package com.argio.resource;

import com.argio.dto.income.RegisterIncomeDto;
import com.argio.dto.income.UpdateIncomeDto;
import com.argio.enums.IncomeCategory;
import com.argio.service.IncomeService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Resource class for managing income-related endpoints.
 *
 * Provides RESTful APIs to perform operations such as retrieving a single income by its ID,
 * fetching a list of incomes, creating a new income record, and updating existing income records.
 * This resource is secured using authentication and consumes/produces JSON data.
 */
@Path( "/incomes")
@Authenticated
@Consumes("application/json")
@Produces("application/json")
public class IncomeResource {

    @Inject IncomeService service;

    /**
     * Retrieves an income record by its unique identifier.
     *
     * This method fetches the income associated with the given ID from the IncomeService
     * and returns it as part of a Response object.
     *
     * @param id the unique identifier of the income record to be retrieved
     * @return a Response object containing the income record if found
     */
    @GET
    @Path("/{id}")
    public Response getIncome(
            @PathParam("id") UUID id
    ) {
        var income = service.get(id);
        return Response.ok(income).build();
    }

    /**
     * Retrieves a list of income records for the currently authenticated user, filtered by the specified
     * date range and income category.
     *
     * @param dateFrom the start date of the range within which the income records must fall;
     *                 if null, defaults to the first day of the current month
     * @param dateTo the end date of the range within which the income records must fall;
     *               if null, defaults to the current date
     * @param category the category of income to filter by; if null, all income categories are included
     * @return a Response object containing a list of income records that match the specified criteria
     */
    @GET
    public Response getIncomeList(
        @QueryParam("dateFrom") LocalDate dateFrom,
        @QueryParam("dateTo") LocalDate dateTo,
        @QueryParam("category") IncomeCategory category
    ) {
        var incomeList = service.getListByUser(dateFrom, dateTo, category);
        return Response.ok(incomeList).build();
    }

    /**
     * Creates a new income record for the authenticated user.
     *
     * This method processes the provided {@link RegisterIncomeDto} to register a new
     * income entry in the system. The income data includes details such as the amount,
     * category, description, and the date the income was received. The request is validated
     * to ensure that all required fields are present and comply with the defined validation
     * constraints.
     *
     * @param incomeRequest the DTO containing the details of the income to be created.
     *                      It must include a non-null, positive amount, a valid income
     *                      category, a non-blank description with a maximum length of
     *                      500 characters, and a date that is not in the future.
     * @return a Response indicating the outcome of the operation. If the income is successfully
     *         created, the response will have a 200 (OK) status code.
     */
    @POST
    public Response createIncome(@Valid RegisterIncomeDto incomeRequest) {
        service.registerIncome(incomeRequest);
        return Response.ok().build();
    }

    /**
     * Updates an existing income record with the details provided in the {@link UpdateIncomeDto}.
     *
     * This method accepts a DTO containing the updated income information, validates the input,
     * and delegates the update operation to the service layer. If the update is successful,
     * an HTTP 200 (OK) response is returned.
     *
     * @param incomeRequest the {@link UpdateIncomeDto} object containing the updated details
     *                      of the income record to be modified. It must include a valid unique
     *                      identifier, a positive monetary amount, a non-blank description,
     *                      a valid category, and a valid date that is not in the future.
     * @return a Response object with an HTTP 200 (OK) status if the update is successfully performed.
     */
    @PUT
    public Response updateIncome(@Valid UpdateIncomeDto incomeRequest) {
        service.updateIncome(incomeRequest);
        return Response.ok().build();
    }
}