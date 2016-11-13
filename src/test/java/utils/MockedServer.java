package utils;

import com.makowski.allegro.recruitment.model.RepoDetails;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by Mateusz Makowski on 13.11.2016.
 */
public class MockedServer extends AbstractVerticle {

    private final static int responseDelay = 10;

    public void runMockServer() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MockedServer());
    }

    public void response(RoutingContext routingContext) {

        routingContext
                .response()
                .setStatusCode(400)
                .end(Json.encodePrettily(new RepoDetails()));
    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        vertx.setTimer(responseDelay, id -> {
            router.get("/repos/makowskimateusz/marks").handler(this::response);
        });

        router.get("/repos/someUser/someRepo").handler(routingContext ->
                routingContext.response()
                        .setStatusCode(500)
                        .end("Internal server error"));

        router.get("/repos/otherUser/otherRepo").handler(routingContext ->
                routingContext.response()
                        .setStatusCode(503)
                        .end("Service unavailable"));

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8081);
    }

}