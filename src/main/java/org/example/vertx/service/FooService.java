package org.example.vertx.service;

import org.example.vertx.domain.Foo;

import java.util.Collection;

public interface FooService {

    void saveFoo(Foo foo);

    Collection<Foo> findAll();

}
