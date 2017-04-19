package org.example.vertx.web.controller.v1;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.example.vertx.domain.Foo;
import org.example.vertx.service.FooService;
import org.example.vertx.web.controller.v1.dto.FooDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FooController {

    @Autowired
    private FooService fooService;

    public void create(RoutingContext routingContext) {
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

    public void findAll(RoutingContext routingContext) {
        Collection<Foo> allFoos = fooService.findAll();
        System.out.println("----");
        System.out.println(allFoos);
        Collection<FooDTO> allFooDTOs = allFoos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        routingContext.response()
                .end(Json.encode(allFooDTOs));
    }

    private Foo mapToDomain(FooDTO fooDTO) {
        return new Foo(
                fooDTO.getId(),
                fooDTO.getBar());
    }

    private FooDTO mapToDTO(Foo foo) {
        return new FooDTO(
                foo.getId(),
                foo.getBar());
    }

}
