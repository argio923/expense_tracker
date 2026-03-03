package com.argio.exception.mapper;

import com.argio.dto.ErrorResponseDto;
import com.argio.exception.model.DomainException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

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