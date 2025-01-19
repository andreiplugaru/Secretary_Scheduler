package ro.uaic.info.exceptions;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", Response.Status.CONFLICT.getStatusCode())
                .add("error", "Conflict")
                .add("message", exception.getMessage())
                .build();

        return Response.status(Response.Status.CONFLICT)
                .entity(jsonResponse.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

