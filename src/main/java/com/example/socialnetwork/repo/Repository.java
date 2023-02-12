package com.example.socialnetwork.repo;

public interface Repository<ID, E> {

    void save(E entity);

    void delete(ID id);

    E findOneById(ID id);

    void update(E entity, E newEntity);

    Iterable<E> findAll();
}
