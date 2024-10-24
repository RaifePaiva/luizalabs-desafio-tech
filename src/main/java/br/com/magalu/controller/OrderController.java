package br.com.magalu.controller;

import br.com.magalu.model.UserModel;
import br.com.magalu.service.ProcessorService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Path("/order")
@Tag(name = "Order", description = "Order operations")
public class OrderController {

    private final ProcessorService processorService;

    public OrderController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(summary = "Process orders from a file", description = "Processes orders from the provided file and returns the users with their orders.")
    public Response process(@RestForm("file") InputStream file,
                            @QueryParam("orderId") Integer orderId,
                            @QueryParam("startDate") String startDate,
                            @QueryParam("endDate") String endDate) {

        if (file == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();
            Set<UserModel> users = processorService.process(lines, orderId, startDate, endDate);
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
