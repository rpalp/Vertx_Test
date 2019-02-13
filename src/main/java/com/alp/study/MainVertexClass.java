package com.alp.study;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedHashMap;
import java.util.Map;


public class MainVertexClass extends AbstractVerticle {

    private Map<Integer, DataStructure> DataRows = new LinkedHashMap<>();

    @Override
    public void start(Future<Void> fut) {

        createSomeData();

        Router router = Router.router(vertx);
        router.get("/").handler(rc -> {
            rc.response().putHeader("content-type", "text/html")
                    .end("Welcome to API Service");
        });

        router.get("/api/getAll").handler(this::getAll);
        router.route("/api*").handler(BodyHandler.create());
        router.post("/api/addOne").handler(this::addOne);
        router.get("/api/getOne/:id").handler(this::getOne);

        vertx.createHttpServer() // creates a HttpServer
                .requestHandler(router) // router will handle the requests
                .listen(8085, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

    private void addOne(RoutingContext routingContext) {
        // Read the request's content and create an instance of Whisky.
        final DataStructure DataRow = Json.decodeValue(routingContext.getBodyAsString(),
                DataStructure.class);
        // Add it to the backend map
        DataRows.put(DataRow.getId(), DataRow);
        // Return the created whisky as JSON
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(DataRow));
    }

    private void getAll(RoutingContext routingContext) {
        // Write the HTTP response
        // The response is in JSON using the utf-8 encoding
        // We returns the list of bottles
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(DataRows.values()));

    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            DataStructure dataStructure = DataRows.get(idAsInteger);
            if (dataStructure == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(dataStructure));
            }
        }
    }

    private void createSomeData() {
        DataStructure firstData = new DataStructure("Aleksej", "letter", "20.01.2019", "24.01.2019", "Moscow", "Leningrad");
        DataRows.put(firstData.getId(), firstData);
        DataStructure secondData = new DataStructure("Andrey", "letter", "22.01.2019", "25.01.2019", "Moscow", "Leningrad");
        DataRows.put(secondData.getId(), secondData);
    }
}
