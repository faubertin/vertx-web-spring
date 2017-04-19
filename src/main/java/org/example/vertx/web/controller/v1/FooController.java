package org.example.vertx.web.controller.v1;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.example.vertx.domain.Foo;
import org.example.vertx.service.FooService;
import org.example.vertx.web.controller.v1.dto.FooDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FooController {

    @Autowired
    private FooService fooService;

    public void createFoo(RoutingContext routingContext) {
        FooDTO fooDTO = Optional.ofNullable(routingContext.getBodyAsJson())
                .map(bodyAsJson -> bodyAsJson.mapTo(FooDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("Empty body provided"));

        fooDTO.setId(UUID.randomUUID().toString());

        Foo foo = mapToDomain(fooDTO);
        fooService.saveFoo(foo);

        routingContext.response()
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end(Json.encode(fooDTO));
    }

    private Foo mapToDomain(FooDTO fooDTO) {
        return new Foo(
                fooDTO.getId(),
                fooDTO.getBar());
    }

}
