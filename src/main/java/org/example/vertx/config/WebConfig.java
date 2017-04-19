package org.example.vertx.config;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.vertx.web.controller.v1.FooController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class WebConfig {


    @Autowired
    private Vertx vertx;

    @Bean
    public HttpServer httpServer() {
        return vertx.createHttpServer();
    }

    @Bean
    public Router router() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        return router;
    }

    @Bean
    public HttpServerBootstrap httpServerBootstrap() {
        return new HttpServerBootstrap();
    }

    @Bean
    public Route findAllFoosRoute() {
        return router().route(HttpMethod.GET, "/foos")
                .handler(routingContext -> fooControllerV1().findAll(routingContext));
    }

    @Bean
    public Route createFooRoute() {
        return router().route(HttpMethod.POST, "/foos")
                .handler(routingContext -> fooControllerV1().create(routingContext));
    }

    @Bean
    public FooController fooControllerV1() {
        return new FooController();
    }

    public static class HttpServerBootstrap {

        @Value("${vertx.server.port}")
        private int port;

        @Autowired
        private HttpServer httpServer;

        @Autowired
        private Router router;

        @Autowired
        private List<Route> routes;

        @PostConstruct
        public void init() {
            httpServer.requestHandler(router::accept);
            httpServer.listen(port);
        }

    }

}
