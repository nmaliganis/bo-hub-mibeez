package com.eu.mibeez.endpoint;


import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.springframework.stereotype.Component;

@Component
@Path("/hub")
public class HubEndpoint {

    @GET
    public String reverse(@QueryParam("input") @NotNull String input) {
        return new StringBuilder(input).reverse().toString();
    }

    @Path("/Shells")
    @GET
    @Produces({"application/json;qs=1"})
    public void getShells(@Suspended final AsyncResponse response) {
/*        ListenableFuture<Collection<Book>> booksFuture = dao.getBooksAsync();
        Futures.addCallback(booksFuture, new FutureCallback<Collection<Book>>() {
            public void onSuccess(Collection<Book> books) {
                response.resume(books);
            }
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
            }
        });*/
    }
}
