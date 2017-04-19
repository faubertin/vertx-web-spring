package org.example.vertx.service;

import org.example.vertx.domain.Foo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class FooServiceImpl implements FooService {

    private final ConcurrentMap<String, Foo> datastore;

    public FooServiceImpl() {
        this.datastore = new ConcurrentHashMap<>();
    }

    @Override
    public void saveFoo(Foo foo) {
        datastore.put(foo.getId(), foo);
    }

    @Override
    public Collection<Foo> findAll() {
        return datastore.values();
    }

}
