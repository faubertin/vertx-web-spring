package org.example.vertx.config;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.vertx.web.controller.v1.FooController;
import org.example.vertx.web.controller.v1.dto.FooDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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
    public Route fooRoute() {
        return router().route("/foos").method(HttpMethod.POST).handler(routingContext -> {
            fooControllerV1().createFoo(routingContext);
        });
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
