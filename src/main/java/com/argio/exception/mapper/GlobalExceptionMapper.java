package com.argio.exception.mapper;

import com.argio.dto.ErrorResponseDto;
import com.argio.exception.model.DomainException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * A global ExceptionMapper implementation for handling exceptions in a RESTful application.
 *
 * This class is responsible for capturing unhandled exceptions that occur during the
 * request lifecycle and converting them into a standardized error response, which is
 * then returned to the client.
 *
 * The implementation provides specific handling for exceptions of type {@code DomainException}.
 * The custom logic extracts the exception message from {@code DomainException} instances
 * and includes it in the response to ensure meaningful error messages are sent to the client.
 *
 * For exceptions not of type {@code DomainException}, a generic internal server error response
 * with a status code of 500 and a default message "Internal server error" is returned.
 *
 * The response body is constructed using the {@code ErrorResponseDto} class, which encapsulates
 * the error message and HTTP status code.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    /**
     * Converts a throwable exception into a RESTful response.
     *
     * This method acts as an exception handler within a global exception mapper.
     * It inspects the provided exception and builds an appropriate HTTP response.
     * If the exception is of type {@code DomainException}, the response includes
     * the message from the exception. For other exception types, a generic
     * internal server error response is returned with a default message.
     *
     * @param ex the exception to be mapped to a RESTful response
     * @return a {@code Response} object encapsulating the HTTP status code and
     *         an {@code ErrorResponseDto} containing the error message and
     *         status code
     */
    @Override
    public Response toResponse(Throwable ex) {
        var status = 500;
        String message = "Internal server error";

        if(ex instanceof DomainException)
            message = ex.getMessage();

        return Response.status(status)
                       .entity(new ErrorResponseDto(message, status))
                       .build();
    }
}