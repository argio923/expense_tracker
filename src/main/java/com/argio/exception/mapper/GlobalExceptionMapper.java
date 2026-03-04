package com.argio.exception.mapper;

import com.argio.dto.ErrorResponseDto;
import com.argio.exception.model.DomainException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Throwable ex) {
        // Logga SEMPRE: così in console vedrai stacktrace e causa reale
        LOG.error("Unhandled exception", ex);

        // Se è una WebApplicationException (es. NotFoundException, BadRequestException, ecc)
        // rispetta lo status invece di trasformare tutto in 500.
        if (ex instanceof WebApplicationException wae) {
            int status = wae.getResponse().getStatus();
            String message = safeMessage(wae.getMessage(), "Request failed");
            return Response.status(status)
                    .entity(new ErrorResponseDto(message, status))
                    .build();
        }

        // Eccezioni di dominio: messaggio “pulito” al client
        if (ex instanceof DomainException) {
            int status = 400; // scegli tu (400/409/404 ecc in base al tipo)
            return Response.status(status)
                    .entity(new ErrorResponseDto(ex.getMessage(), status))
                    .build();
        }

        // Tutto il resto: 500 generico
        int status = 500;
        return Response.status(status)
                .entity(new ErrorResponseDto("Internal server error", status))
                .build();
    }

    private String safeMessage(String message, String fallback) {
        return (message == null || message.isBlank()) ? fallback : message;
    }
}